package com.app.sportbetting.models.createComp

data class CreateCompModel(
    var sport_name: String,
    var password: String,
    var comp_name: String,
    var points: Int,
    var price: Int,
    var first_game: Int,
    var every_game: Int,
    var tip_for_winner: Int,
    var tip_for_draw: Int,
    var perfect_rounds: Int,
    var bonus_for_round: Int,
    var non_submission: String,
    var join_late: String,
    var finals: Int,
    var hide_ladder: String,
    var hide_tipster_name: Int,
    var betting_odds: Int,
    var prize_info: String,
    var knockout_comp: String,
    var joker_round: String,
    var margin_rule: String,
    var prize_mangement: String,
    var sports_id:Int,
) {

}