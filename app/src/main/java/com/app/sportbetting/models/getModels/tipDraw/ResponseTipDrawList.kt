package com.app.sportbetting.models.getModels.tipDraw

data class ResponseTipDrawList(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
)