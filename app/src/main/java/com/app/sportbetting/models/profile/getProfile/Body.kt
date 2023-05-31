package com.app.sportbetting.models.profile.getProfile

data class Body(
    val country: String,
    val created_at: String,
    val deviceToken: Any,
    val deviceType: Any,
    val dob: String,
    val email: String,
    val fav_sport: String,
    val fav_team: String,
    val gender: String,
    val id: Int,
    val name: String,
    val password: String,
    val postcode: String,
    val profile_pic: String,
    val surname: String,
    val time_zone: String,
    val tipping_name: String
)