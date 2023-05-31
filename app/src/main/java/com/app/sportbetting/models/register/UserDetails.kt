package com.app.sportbetting.models.register

data class UserDetails(
    val country: String,
    val created_at: CreatedAt,
    val dob: String,
    val email: String,
    val fav_sport: String,
    val fav_team: String,
    val gender: String,
    val id: Int,
    val name: String,
    val password: String,
    val postcode: String,
    val surname: String,
    val time_zone: String,
    val tipping_name: String
)