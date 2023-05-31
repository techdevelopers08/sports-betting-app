package com.app.sportbetting.models.submitTip

data class Body(
    val created_at: CreatedAt,
    val id: Int,
    val margin: String,
    val participant1: String,
    val participant2: String,
    val selected_team: String,
    val sport_name: String,
    val sports_id: Int,
    val start_date: String,
    val status_type: String,
    val tournamentStageFk: String,
    val tournament_fk: String,
    val tournament_name: String,
    val tournament_stage_name: String,
    val tournament_template_fk: String,
    val tournament_template_name: String,
    val user_id: Int,
    val venue_name: String
)