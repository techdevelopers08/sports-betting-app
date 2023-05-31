package com.app.sportbetting.models.rugbyLeague

import com.google.gson.annotations.SerializedName

data class TeamX(
    @field:SerializedName("@attributes")
    val attributes: AttributesXX,

    @field:SerializedName("@value")
    val value: String
)