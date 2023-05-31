package com.app.sportbetting.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sportbetting.models.DailyEventsModel
import com.app.sportbetting.models.SportsModel
import com.app.sportbetting.models.SubmitTipModel
import com.app.sportbetting.models.oddModel.ResponseOdds
import com.app.sportbetting.models.rugbyLeague.ResponseRugbyLeague
import com.app.sportbetting.models.submitTip.ResponseSubmitTips
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.collections.ArrayList

class SportsViewModel(val application: Application, val repository: Repository) : ViewModel() {

    var data = MutableLiveData<ArrayList<SportsModel>>()
    var dailyEventResult = MutableLiveData<ArrayList<DailyEventsModel>>()

    val submitTipsResult = MutableLiveData<Resource<ResponseSubmitTips>>()
    val resultDailyMatches = MutableLiveData<Resource<ResponseOdds>>()

    private var list: ArrayList<SportsModel>? = null

    private var dailyEventList= ArrayList<DailyEventsModel>()

    val resultRugbyLeague = MutableLiveData<Resource<ResponseRugbyLeague>>()



    fun getSportsList(username: String, token: String, tz: String) {
        list = ArrayList()
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = repository.sportsList(username, token, tz)
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {
                        val body: String? = response.body()?.string()
                        Log.d("TAG", "getSportsList: $body")

                        val json = JSONObject(body)

                        val jsonObject = json.getJSONObject("sports")
                        val iterator = jsonObject.keys()


                        while (iterator.hasNext()) {

                            val key = iterator.next().toString()
                            val value = jsonObject.getJSONObject(key)



                            list?.add(
                                SportsModel(
                                    value.getString("name"),
                                    null,
                                    value.getString("id").toInt()
                                )
                            )
                            if (list != null) {
                                data.value = list!!
                            }

                        }
                        Log.d("TAG", "getSportsList: ${list?.size}")

                    }
                }


            } catch (t: Throwable) {
                Log.d("TAG", "onViewModel: $t")
            }
        }


    }

    fun getDailyEvents(
        username: String,
        token: String,
        tz: String,
        sportsFk: String,
        languageFk: String,
        date: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response =
                    repository.getEventsDaily(username, token, tz, sportsFk, languageFk, date)

                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {
                        val body: String? = response.body()?.string()

                        val json = JSONObject(body!!)

                        val jsonObject = json.getJSONObject("events")

                        val iterator = jsonObject.keys()

                        while (iterator.hasNext()) {

                            val key = iterator.next().toString()
                            var venueName = ""
                            val value = jsonObject.getJSONObject(key)
                            val name = value.getString("name").split("-")
                            val participant1 = name[0]
                            val participant2 = name[1]
                            val tournamentFk = value.getString("name")
                            val tournamentName = value.getString("tournament_name")
                            val tournamentStageFk = value.getString("tournament_stageFK")
                            val statusType = value.getString("status_type")
                            val tournamentTemplateName = value.getString("tournament_template_name")
                            val sportName = value.getString("sport_name")
                            val sports_Fk = value.getString("sportFK")
                            val tournamentTemplateFK = value.getString("tournament_templateFK")
                            val startDate = value.getString("startdate").split("T")
                            val date = startDate[0]
                            val time = startDate[1].split("+")
                            val formattedTime = time[0]
                            var tournamentStageName = value.getString("tournament_stage_name")
                            val property = value.getJSONObject("property")
                            val nestedIterator = property.keys()
                            while (nestedIterator.hasNext()) {
                                val nestedKey = nestedIterator.next().toString()
                                val nestedObject = property.getJSONObject(nestedKey)
                                val nestedName = nestedObject.getString("name")
                                if (nestedName == "VenueName") {
                                    venueName = nestedObject.getString("value")
                                    break
                                }
                            }

                            dailyEventList.add(
                                DailyEventsModel(
                                    sportName,
                                    sports_Fk,
                                    tournamentFk,
                                    tournamentName,
                                    tournamentStageFk,
                                    participant1,
                                    participant2,
                                    statusType,
                                    tournamentTemplateName,
                                    tournamentTemplateFK,
                                    tournamentStageName,
                                    venueName,
                                    date,
                                    formattedTime
                                )
                            )

                            Log.d("TAG", "getDailyEvents: $dailyEventList")

                            dailyEventResult.value = dailyEventList

                        }
                    }
                }


            } catch (t: Throwable) {
                Log.d("TAG", "onViewModel: $t")
                dailyEventResult.postValue(dailyEventList)
            }
        }


    }

    fun submitTips(body:SubmitTipModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.submitTips(body)
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                submitTipsResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                submitTipsResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            submitTipsResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 401) {

                            submitTipsResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            submitTipsResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            submitTipsResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        submitTipsResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        submitTipsResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        submitTipsResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }

    fun getDailyMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getDailyMatches()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        resultDailyMatches.value = Resource.success(
                            data = response.body()!!,
                            message = "error fetching datA"
                        )
                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "getDailyMatches: $t")
            }
        }

    }


    fun getDailyMatchesRl() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMatchesRl()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        resultRugbyLeague.value = Resource.success(
                            data = response.body()!!,
                            message = "error fetching datA"
                        )
                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "getDailyMatches: $t")
            }
        }

    }



}