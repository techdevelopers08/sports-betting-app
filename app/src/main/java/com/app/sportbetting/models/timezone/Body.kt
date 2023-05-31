package com.app.sportbetting.models.timezone

data class Body(
    val country_code: String,
    val dst_offset: Int,
    val gmt_offset: Int,
    val raw_offset: Int,
    val timezone: String
)