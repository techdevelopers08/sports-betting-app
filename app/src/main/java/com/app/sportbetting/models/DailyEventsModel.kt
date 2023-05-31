package com.app.sportbetting.models

data class DailyEventsModel(
    var sportsName: String,
    var sportsId: String,
    var tournamentFK: String,
    var tournamentName: String,
    var tournamentStageFk: String,
    var participant1: String,
    var participant2: String,
    var statusType: String,
    var tournamentTemplateName: String,
    var tournamentTemplateFk: String,
    var tournamentStageName: String,
    var venueName: String,
    var startDate: String,
    var time: String,
)
