package olguin.amos.practicaautenticacionolguina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth

        val email: EditText = findViewById(R.id.etrEmail)
        val password: EditText = findViewById(R.id.etrPassword)
        val confirmPassword: EditText = findViewById(R.id.etrConfirmPassword)
        val error: TextView = findViewById(R.id.tvrError)

        val btnRegistrar: Button = findViewById(R.id.btnRegister)
        val btnRegresar: Button = findViewById(R.id.btnRegresar)

        btnRegresar.setOnClickListener{

            startActivity(Intent(this, LoginActivity::class.java))

        }

        error.visibility = View.VISIBLE

        btnRegistrar.setOnClickListener{

            if(email.text.isEmpty() || password.text.isEmpty() || confirmPassword.text.isEmpty()){

                error.text = "Todos los campos deben ser llenados"
                error.visibility = View.VISIBLE

            }else if(!password.text.toString().equals(confirmPassword.text.toString())){

                error.text = "Las contraseñas no coinciden"
                error.visibility = View.VISIBLE

            }else{

                error.visibility = View.INVISIBLE
                sigIn(email.text.toString(), password.text.toString())

            }

        }

    }

    fun sigIn(email: String, password: String){

        Log.d("INFO", "email: ${email}, password: ${password}")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {

            task -> if(task.isSuccessful){

                Log.d("INFO", "signInWithEmail: Success")
                val user = auth.currentUser
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }else {

                Log.w("ERROR", "signInWithEmail: failure", task.exception)
                Toast.makeText(baseContext, "El registro fallo.", Toast.LENGTH_SHORT).show()

            }


        }

    }

}