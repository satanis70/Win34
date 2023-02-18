package com.example.win34

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.win34.databinding.ActivityMainBinding
import com.example.win34.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var result: Drawable?
        CoroutineScope(Dispatchers.IO).launch {
            result = getBitmap()
            launch(Dispatchers.Main) {
                binding.constraintLayoutMainScreen.background = result
            }
        }
        binding.buttonStartQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
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