package com.app.sportbetting.network

import com.app.sportbetting.models.SubmitTipModel
import com.app.sportbetting.models.changePassword.ChangePasswordModel
import com.app.sportbetting.models.changePassword.ResponseChangePassword
import com.app.sportbetting.models.createComp.CreateCompModel
import com.app.sportbetting.models.createComp.ResponseCreateComp
import com.app.sportbetting.models.getComp.ResponseGetCompList
import com.app.sportbetting.models.getModels.hideLadder.ResponseHideLadderList
import com.app.sportbetting.models.getModels.submissionList.ResponseSubmissionList
import com.app.sportbetting.models.getModels.tipDraw.ResponseTipDrawList
import com.app.sportbetting.models.joinComp.JoinCompModel
import com.app.sportbetting.models.joinComp.ResponseJoinComp
import com.app.sportbetting.models.login.LoginDetailsModel
import com.app.sportbetting.models.login.ResponseLogin
import com.app.sportbetting.models.logout.ResponseLogout
import com.app.sportbetting.models.oddModel.ResponseOdds
import com.app.sportbetting.models.profile.getProfile.ResponseGetProfile
import com.app.sportbetting.models.profile.getProfile.updateProfile.ResponseUpdateProfile
import com.app.sportbetting.models.register.RegisterModel
import com.app.sportbetting.models.register.ResponseRegistration
import com.app.sportbetting.models.rugbyLeague.ResponseRugbyLeague
import com.app.sportbetting.models.submitTip.ResponseSubmitTips
import com.app.sportbetting.models.timezone.ResponseTimezone
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {
    @POST("/users/register")
    suspend fun register(@Body body: RegisterModel): Response<ResponseRegistration>

    @POST("/users/login")
    suspend fun login(@Body body: LoginDetailsModel): Response<ResponseLogin>

    @GET("/users/getTipDrawList")
    suspend fun getTipDrawList(): Response<ResponseTipDrawList>

    @GET("/users/getHideLadderRound")
    suspend fun getLadderRound(): Response<ResponseHideLadderList>

    @GET("/users/getSubmissionList")
    suspend fun getSubmissionList(): Response<ResponseSubmissionList>

    @POST("/users/createComp")
    suspend fun createComp(@Body createComp: CreateCompModel): Response<ResponseCreateComp>

    @GET("/users/getCompList")
    suspend fun getCompList(): Response<ResponseGetCompList>

    @GET("/users/getProfile")
    suspend fun getProfile(): Response<ResponseGetProfile>

    @Multipart
    @POST("/users/updateProfile")
    suspend fun updateProfile(
        @Part("tipping_name") tipping_name: RequestBody,
        @Part profilePic: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("surname") surname: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("country") country: RequestBody,

        ): Response<ResponseUpdateProfile>


    @POST("/users/joinRoom")
    suspend fun joinComp(@Body joinComp: JoinCompModel): Response<ResponseJoinComp>



    @GET("sport/list/")
    suspend fun sportsList(
        @Query("username") userName: String,
        @Query("token") token: String,
        @Query("tz") tz: String
    ): Response<ResponseBody>

    @GET("/tournament_template/list/")
    suspend fun getTournamentTemplate(
        @Query("username") userName: String,
        @Query("token") token: String,
        @Query("tz") tz: String,
        @Query("sportFK") sportFK: String
    )


    @GET("/tournament/list/")
    suspend fun getTournament(
        @Query("username") userName: String,
        @Query("token") token: String,
        @Query("tz") tz: String,
        @Query("sportFK") sportFK: String
    )


    @GET("/tournament_stage/list/")
    suspend fun getTournamentStage(
        @Query("username") userName: String,
        @Query("token") token: String,
        @Query("tz") tz: String,
        @Query("sportFK") sportFK: String
    )


    @GET("event/daily/")
    suspend fun getDailyEvents(
        @Query("username") userName: String,
        @Query("token") token: String,
        @Query("tz") tz: String,
        @Query("sportFK") sport: String,
        @Query("language_typeFK") language: String,
        @Query("date") date: String,
    ): Response<ResponseBody>

    @POST("/users/createMargin")
    suspend fun submitTips(@Body body:SubmitTipModel):Response<ResponseSubmitTips>

    @POST("/users/changePassword")
    suspend fun resetPassword(@Body body:ChangePasswordModel):Response<ResponseChangePassword>

    @GET("/users/logout")
    suspend fun logout():Response<ResponseLogout>

    @GET("/users/timezone")
    suspend fun getTimezone():Response<ResponseTimezone>


    @GET("competition.php")
    suspend fun getMatches(
        @Query("competition") competition: Int,
        @Query("season_id") season_id: Int,
        @Query("feed_type") feed_type: String,
        @Query("user") user: String,
        @Query("psw") psw: String,
        @Query("json") type:String
    ): Response<ResponseOdds>

    @GET("competition.php")
    suspend fun getMatchesRl(
        @Query("competition") competition: Int,
        @Query("season_id") season_id: Int,
        @Query("feed_type") feed_type: String,
        @Query("user") user: String,
        @Query("psw") psw: String,
        @Query("json") type:String
    ): Response<ResponseRugbyLeague>




}