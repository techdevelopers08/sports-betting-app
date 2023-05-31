package com.app.sportbetting.models.rugbyLeague

import com.google.gson.annotations.SerializedName

data class Team(
    @field:SerializedName("@attributes")

    val attributes: AttributesX,
    @field:SerializedName(" @value")
    val value: String
)