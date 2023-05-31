package com.app.sportbetting.models.logout

data class ResponseLogout(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)