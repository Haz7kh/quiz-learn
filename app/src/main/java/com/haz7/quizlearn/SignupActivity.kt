package com.haz7.quizlearn



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.haz7.quizlearn.databinding.ActivityLoginBinding
import com.haz7.quizlearn.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    lateinit var signupBinding: ActivitySignupBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signupBinding = ActivitySignupBinding.inflate(layoutInflater)

        val view = signupBinding.root
        setContentView(view)

        signupBinding.btnSignup.setOnClickListener {
           val email = signupBinding.editTextSignupEmail.text.toString()
            val password = signupBinding.editTextSignupPassword.text.toString()

            signupWithFireBase(email, password)
        }

    }

    private fun signupWithFireBase(email: String, password: String) {

        signupBinding.progressBarSignup.visibility = View.VISIBLE
        signupBinding.btnSignup.isClickable = false
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,"Your account has been created successfully",Toast.LENGTH_SHORT).show()
                finish()
                signupBinding.progressBarSignup.visibility = View.INVISIBLE
                signupBinding.btnSignup.isClickable = true
            }else {
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }
}