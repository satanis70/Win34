package com.example.win34

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.win34.databinding.ActivityMainBinding
import com.example.win34.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var result: Drawable?
        CoroutineScope(Dispatchers.IO).launch {
            result = getBitmap()
            launch(Dispatchers.Main) {
                binding.constraintLayoutSplashScreen.background = result
                val timerSplash = object : CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {
                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                timerSplash.start()
            }
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