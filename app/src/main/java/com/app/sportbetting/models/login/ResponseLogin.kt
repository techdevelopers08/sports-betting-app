package com.app.sportbetting.models.login

data class ResponseLogin(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)