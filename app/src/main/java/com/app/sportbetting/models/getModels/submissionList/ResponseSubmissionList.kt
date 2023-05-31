package com.app.sportbetting.models.getModels.submissionList

data class ResponseSubmissionList(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
)