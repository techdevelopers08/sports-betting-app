package com.app.sportbetting.models.register

data class ResponseRegistration(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)