package com.app.sportbetting.models.timezone

data class ResponseTimezone(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
)