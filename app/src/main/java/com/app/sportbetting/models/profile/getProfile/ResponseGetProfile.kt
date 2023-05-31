package com.app.sportbetting.models.profile.getProfile

data class ResponseGetProfile(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)