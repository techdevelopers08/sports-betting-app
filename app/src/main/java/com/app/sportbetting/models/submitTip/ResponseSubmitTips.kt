package com.app.sportbetting.models.submitTip

data class ResponseSubmitTips(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)