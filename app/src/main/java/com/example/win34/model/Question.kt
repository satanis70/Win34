package com.example.win34.model

import androidx.annotation.Keep

@Keep
data class Question(
    val answer1: Answer1,
    val answer2: Answer1,
    val answer3: Answer1,
    val answer4: Answer1,
    val image: String,
    val question: String
)