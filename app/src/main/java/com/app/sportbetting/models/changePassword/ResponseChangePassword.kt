package com.app.sportbetting.models.changePassword

data class ResponseChangePassword(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)