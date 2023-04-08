package com.haz7.quizlearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.haz7.quizlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

     lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)




        mainBinding.btnStartQuiz.setOnClickListener {
            val intent = Intent(this,QuizActivity::class.java)
            startActivity(intent)
        }


        val animationView = mainBinding.animationView
        animationView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                animationView.setAnimation(R.raw.home)
                animationView.repeatCount = LottieDrawable.INFINITE
                animationView.playAnimation()
            }

            override fun onViewDetachedFromWindow(v: View) {
                animationView.cancelAnimation()
            }
        })






    }

    //menu items
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val homeMenuItem = menu.findItem(R.id.menu_home)
        homeMenuItem.setOnMenuItemClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {



            R.id.menu_score -> {
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.menu_logout -> {
                //email and password logout:
                FirebaseAuth.getInstance().signOut()

                // google account logout:
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail().build()
                val googleSignInClient = GoogleSignIn.getClient(this,gso)
                googleSignInClient.signOut().addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Sign out is successful",Toast.LENGTH_SHORT).show()
                    }

                }

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }



}