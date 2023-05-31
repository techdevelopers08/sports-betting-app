package com.app.sportbetting.models.rugbyLeague

import com.google.gson.annotations.SerializedName

data class Fixture(
    @field:SerializedName("@attributes")
    val attributes: Attributes,
    val team: List<Team>
)