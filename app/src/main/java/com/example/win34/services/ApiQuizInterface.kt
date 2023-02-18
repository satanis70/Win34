package com.example.win34.services


import com.example.win34.model.QuizModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiQuizInterface {
    @GET("question.json")
    fun getData(): Call<QuizModel>
}