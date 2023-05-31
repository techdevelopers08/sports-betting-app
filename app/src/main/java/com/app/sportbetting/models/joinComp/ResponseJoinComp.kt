package com.app.sportbetting.models.joinComp

data class ResponseJoinComp(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)