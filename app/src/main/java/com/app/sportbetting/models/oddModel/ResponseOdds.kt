package com.app.sportbetting.models.oddModel

import com.google.gson.annotations.SerializedName

data class ResponseOdds(

	@field:SerializedName("report")
	val report: Report? = null
)

data class MatchItem(

	@field:SerializedName("@value")
	val value: String? = null,

	@field:SerializedName("@attributes")
	val attributes: Attributes? = null
)

data class Report(

	@field:SerializedName("fixture")
	val fixture: Fixture? = null,

	@field:SerializedName("@attributes")
	val attributes: Attributes? = null
)

data class Attributes(

	@field:SerializedName("jobId")
	val jobId: String? = null,

	@field:SerializedName("reportName")
	val reportName: String? = null,

	@field:SerializedName("competitionId")
	val competitionId: String? = null,

	@field:SerializedName("competitionName")
	val competitionName: String? = null,

	@field:SerializedName("homeSquadScore")
	val homeSquadScore: String? = null,

	@field:SerializedName("venueTimezone")
	val venueTimezone: String? = null,

	@field:SerializedName("matchName")
	val matchName: String? = null,

	@field:SerializedName("awaySquadName")
	val awaySquadName: String? = null,

	@field:SerializedName("homeSquadId")
	val homeSquadId: String? = null,

	@field:SerializedName("awaySquadScore")
	val awaySquadScore: String? = null,

	@field:SerializedName("venueName")
	val venueName: String? = null,

	@field:SerializedName("matchStatus")
	val matchStatus: String? = null,

	@field:SerializedName("roundNumber")
	val roundNumber: String? = null,

	@field:SerializedName("homeSquadName")
	val homeSquadName: String? = null,

	@field:SerializedName("utcStartTime")
	val utcStartTime: String? = null,

	@field:SerializedName("venueId")
	val venueId: String? = null,

	@field:SerializedName("awaySquadId")
	val awaySquadId: String? = null,

	@field:SerializedName("localStartTime")
	val localStartTime: String? = null,

	@field:SerializedName("matchId")
	val matchId: String? = null,

	@field:SerializedName("matchNumber")
	val matchNumber: String? = null
)

data class Fixture(

	@field:SerializedName("match")
	val match: List<MatchItem?>? = null,

	@field:SerializedName("@attributes")
	val attributes: Attributes? = null
)
