package com.app.sportbetting.models.createComp

data class Body(
    val betting_odds: Int,
    val bonus_for_round: Int,
    val comp_name: String,
    val every_game: Int,
    val finals: Int,
    val first_game: Int,
    val hide_ladder: String,
    val hide_tipster_name: Int,
    val id: Int,
    val join_late: String,
    val joker_round: String,
    val knockout_comp: String,
    val margin_rule: String,
    val non_submission: String,
    val password: String,
    val perfect_rounds: Int,
    val points: Int,
    val price: Int,
    val prize_info: String,
    val prize_mangement: String,
    val sport_name: String,
    val tip_for_draw: Int,
    val tip_for_winner: Int
)