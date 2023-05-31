package com.app.sportbetting.models.getModels.hideLadder

data class ResponseHideLadderList(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
)