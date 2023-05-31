package com.app.sportbetting.models.register

data class RegisterModel(
    val tipping_name: String,
    val email: String,
    val name: String,
    val surname: String,
    val dob: String,
    val gender: String,
    val country: String,
    val fav_sport: String,
    val postcode: String,
    val fav_team: String,
    val time_zone: String,
    val password: String,

)