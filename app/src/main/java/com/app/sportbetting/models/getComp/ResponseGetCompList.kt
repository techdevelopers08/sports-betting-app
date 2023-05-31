package com.app.sportbetting.models.getComp

data class ResponseGetCompList(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
)