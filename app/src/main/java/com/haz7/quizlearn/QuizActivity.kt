package com.haz7.quizlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.haz7.quizlearn.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    lateinit var quizBinding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)

        quizBinding.buttonNext.setOnClickListener{

        }

        quizBinding.buttonFinish.setOnClickListener {

        }

        quizBinding.textViewA.setOnClickListener{

        }
        quizBinding.textViewB.setOnClickListener{

        }
        quizBinding.textViewC.setOnClickListener{

        }
        quizBinding.textViewD.setOnClickListener{

        }
    }
}