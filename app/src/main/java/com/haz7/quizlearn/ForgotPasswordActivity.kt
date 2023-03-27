package com.haz7.quizlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.haz7.quizlearn.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var forgotBinding : ActivityForgotPasswordBinding
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        forgotBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = forgotBinding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        forgotBinding.btnReset.setOnClickListener {
            val userEmail = forgotBinding.editTextForgotEmail.text.toString()

            auth.sendPasswordResetEmail(userEmail).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(applicationContext,"We sent a password reset mail to your email address",Toast.LENGTH_SHORT).show()
                  finish()

                }    else {
                    Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}