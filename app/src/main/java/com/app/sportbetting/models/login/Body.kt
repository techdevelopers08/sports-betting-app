package com.app.sportbetting.models.login

data class Body(
    val token: String,
    val userDetails: UserDetails
)