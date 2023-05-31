package com.app.sportbetting.models.createComp

data class ResponseCreateComp(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)