package com.example.win34

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.win34.databinding.ActivityQuizBinding
import com.example.win34.model.QuizModel
import com.example.win34.services.ApiQuizInterface
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private var arrayListQuizModel = ArrayList<QuizModel>()
    private var position = 0
    private lateinit var buttonAnswer1: AppCompatButton
    private lateinit var buttonAnswer2: AppCompatButton
    private lateinit var buttonAnswer3: AppCompatButton
    private lateinit var buttonAnswer4: AppCompatButton
    private lateinit var buttonNext: Button
    private lateinit var imgQuiz: ImageView
    private lateinit var tvQuiz: TextView
    private var rightAnswer = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initOne()
        buttonAnswer1 = binding.buttonAnswer1
        buttonAnswer2 = binding.buttonAnswer2
        buttonAnswer3 = binding.buttonAnswer3
        buttonAnswer4 = binding.buttonAnswer4
        buttonNext = binding.buttonNext
        imgQuiz = binding.imageViewQuiz
        tvQuiz = binding.textViewQuestion
        var result: Drawable?
        CoroutineScope(Dispatchers.IO).launch {
            result = getBitmap()
            launch(Dispatchers.Main) {
                binding.constraintLayoutQuizScreen.background = result
                getData(position)
                buttonNext.setOnClickListener {
                    position=position+1
                    if (arrayListQuizModel[0].questions.size==position){
                        showDialog("$rightAnswer/${arrayListQuizModel[0].questions.size}")
                    } else {
                        getData(position)
                    }
                }
            }
        }
    }

    private fun getData(_position: Int){
        arrayListQuizModel.clear()
        buttonNext.isEnabled = false
        CoroutineScope(Dispatchers.IO).launch {
            val apiQuiz = Retrofit.Builder()
                .baseUrl("http://49.12.202.175/win34/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiQuizInterface::class.java)
            val response = apiQuiz.getData().awaitResponse()
            if (response.isSuccessful){
                arrayListQuizModel.add(response.body()!!)
                launch(Dispatchers.Main){
                    buttonEnabled()
                    buttonSetResource()
                    imgQuiz.load(arrayListQuizModel[0].questions[_position].image)
                    tvQuiz.text = arrayListQuizModel[0].questions[_position].question
                    buttonAnswer1.text = arrayListQuizModel[0].questions[_position].answer1.name
                    buttonAnswer2.text = arrayListQuizModel[0].questions[_position].answer2.name
                    buttonAnswer3.text = arrayListQuizModel[0].questions[_position].answer3.name
                    buttonAnswer4.text = arrayListQuizModel[0].questions[_position].answer4.name
                    buttonAnswer1.setOnClickListener {
                        if (arrayListQuizModel[0].questions[_position].answer1.trueorfalse == "true"){
                            rightAnswer = rightAnswer+1
                            setBackgroundResource(buttonAnswer1, true)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        } else {
                            setBackgroundResource(buttonAnswer1, false)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        }
                    }
                    buttonAnswer2.setOnClickListener {
                        if (arrayListQuizModel[0].questions[_position].answer2.trueorfalse == "true"){
                            rightAnswer = rightAnswer+1
                            setBackgroundResource(buttonAnswer2, true)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        } else {
                            setBackgroundResource(buttonAnswer2, false)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        }
                    }
                    buttonAnswer3.setOnClickListener {
                        if (arrayListQuizModel[0].questions[_position].answer3.trueorfalse == "true"){
                            rightAnswer = rightAnswer+1
                            setBackgroundResource(buttonAnswer3, true)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        } else {
                            setBackgroundResource(buttonAnswer3, false)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        }
                    }
                    buttonAnswer4.setOnClickListener {
                        if (arrayListQuizModel[0].questions[_position].answer4.trueorfalse == "true"){
                            rightAnswer = rightAnswer+1
                            setBackgroundResource(buttonAnswer4, true)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        } else {
                            setBackgroundResource(buttonAnswer4, false)
                            buttonAnswer1.isEnabled = false
                            buttonAnswer2.isEnabled = false
                            buttonAnswer3.isEnabled = false
                            buttonAnswer4.isEnabled = false
                            buttonNext.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun initOne(){
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
    }

    private fun showDialog(result: String){
        val alertDialog = AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background)
        alertDialog.apply {
            setTitle(resources.getString(R.string.result))
            setMessage(result)
            setPositiveButton(resources.getString(R.string.again)) { _: DialogInterface?, _: Int ->
                startActivity(Intent(this@QuizActivity, QuizActivity::class.java))
                finish()
            }
            setOnDismissListener {
                startActivity(Intent(this@QuizActivity, QuizActivity::class.java))
                finish()
            }

        }.create().show()
    }

    private fun buttonEnabled(){
        buttonAnswer1.isEnabled = true
        buttonAnswer2.isEnabled = true
        buttonAnswer3.isEnabled = true
        buttonAnswer4.isEnabled = true
    }
    private fun buttonSetResource(){
        buttonAnswer1.setBackgroundResource(R.drawable.rounded_corner_button)
        buttonAnswer2.setBackgroundResource(R.drawable.rounded_corner_button)
        buttonAnswer3.setBackgroundResource(R.drawable.rounded_corner_button)
        buttonAnswer4.setBackgroundResource(R.drawable.rounded_corner_button)
    }
    private fun setBackgroundResource(buttonAnswer: AppCompatButton, trueOrFalse: Boolean) {
        if (trueOrFalse){
            buttonAnswer.setBackgroundResource(R.drawable.rounded_corner_button_true)
        } else {
            buttonAnswer.setBackgroundResource(R.drawable.rounded_corner_button_false)
        }
    }

    private suspend fun getBitmap(): Drawable {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data("http://49.12.202.175/win34/back.jpg")
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return result
    }
}