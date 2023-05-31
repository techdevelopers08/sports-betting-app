package com.app.sportbetting.models.joinComp

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
    val joker_round: Any,
    val knockout_comp: Any,
    val margin_rule: Any,
    val non_submission: String,
    val password: String,
    val perfect_rounds: Int,
    val points: Int,
    val price: Int,
    val prize_info: Any,
    val prize_mangement: Any,
    val sport_name: String,
    val tip_for_draw: String,
    val tip_for_winner: Int,
    val user_id: Int,
    val sports_id:Int
)