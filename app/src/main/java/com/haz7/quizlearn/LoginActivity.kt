package com.haz7.quizlearn

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.haz7.quizlearn.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    private var currentLanguage = "en"

    val auth = FirebaseAuth.getInstance()

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var activityResultLuncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        val textOfGoogleBtn = loginBinding.btnGoogleSignIn.getChildAt(0) as TextView
        textOfGoogleBtn.text = " Continue with Google"
        textOfGoogleBtn.setTextColor(Color.BLACK)
        // the F here means that the value is floating
        textOfGoogleBtn.textSize = 18F

        //Register
        registerActivityForGoogleSignIn()


       loginBinding.btnSignIn.setOnClickListener{
         val userEmail = loginBinding.editTextLoginEmail.text.toString()
           val userPassword = loginBinding.editTextLoginPassword.text.toString()

           signInUser(userEmail,userPassword)
       }
        loginBinding.btnGoogleSignIn.setOnClickListener{
            signInGoogle()
        }
        // no need for finish() . COZ Signup is child of Login
        loginBinding.textViewSignup.setOnClickListener{
           val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        loginBinding.textViewForgotPassword.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }
    }

    fun signInUser(userEmail: String, userPassword:String) {

     auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener { task ->
         if(task.isSuccessful){
            Toast.makeText(applicationContext,"Welcome to Quiz&Learn App ",Toast.LENGTH_SHORT).show()
             val intent = Intent(this@LoginActivity, MainActivity::class.java)
             startActivity(intent)
             finish()
         }else {
             Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
         }

     }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if(user !=null){
            Toast.makeText(applicationContext,"Welcome to Quiz&Learn App ",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("579158177675-20d3n1n6q4m0bnm390huqto25ick097v.apps.googleusercontent.com")
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        signIn()
    }
    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        activityResultLuncher.launch(signInIntent)
    }
    private fun registerActivityForGoogleSignIn() {
      activityResultLuncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
          ActivityResultCallback{ result ->
              val resultCode = result.resultCode
              val data = result.data
              if (resultCode == RESULT_OK && data !=null) {
                  val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                  firebaseSignInWithGoogle(task)
              }

      })
    }
    private fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount> ){
        try {
            val account : GoogleSignInAccount = task.getResult(ApiException::class.java)
            Toast.makeText(applicationContext,"Welcome to Quiz & Learn ",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            firebaseGoogleAccount(account)

        }catch (e : ApiException){
            Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }

    }

    private fun firebaseGoogleAccount(account: GoogleSignInAccount){

        val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
            if (task.isSuccessful){
               // val user = auth.currentUser

            }else{

            }
        }
    }
}