package com.app.sportbetting.network

import com.app.nplex.network.Api
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
import com.app.sportbetting.models.profile.getProfile.updateProfile.ProfileParamModel
import com.app.sportbetting.models.profile.getProfile.updateProfile.ResponseUpdateProfile
import com.app.sportbetting.models.register.RegisterModel
import com.app.sportbetting.models.register.ResponseRegistration
import com.app.sportbetting.models.rugbyLeague.ResponseRugbyLeague
import com.app.sportbetting.models.submitTip.ResponseSubmitTips
import com.app.sportbetting.models.timezone.ResponseTimezone
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class Repository {
    companion object {
        var getApi = Api.getApi
    }

    suspend fun onRegister(body: RegisterModel): Response<ResponseRegistration> {
        return getApi.register(body)
    }

    suspend fun onLogin(body: LoginDetailsModel): Response<ResponseLogin> {
        return getApi.login(body)
    }

    suspend fun submissionList():Response<ResponseSubmissionList>{
        return getApi.getSubmissionList()
    }
    suspend fun hideLadderList():Response<ResponseHideLadderList>{
        return getApi.getLadderRound()
    }
    suspend fun tipDrawList():Response<ResponseTipDrawList>{
        return getApi.getTipDrawList()
    }

    suspend fun createComp(body: CreateCompModel):Response<ResponseCreateComp>{
        return  getApi.createComp(body)
    }

    suspend fun getCompList():Response<ResponseGetCompList>{
        return getApi.getCompList()
    }

    suspend fun getProfile():Response<ResponseGetProfile>{
        return getApi.getProfile()
    }

    suspend fun updateProfile(body:ProfileParamModel,file:File):Response<ResponseUpdateProfile>{
        val firstName = body.first_name.toRequestBody(MultipartBody.FORM)
        val tippingName = body.tipping_name.toRequestBody(MultipartBody.FORM)

        val surname = body.surname.toRequestBody(MultipartBody.FORM)
        val gender = body.gender.toRequestBody(MultipartBody.FORM)
        val dob = body.dob.toRequestBody(MultipartBody.FORM)
        val country = body.country.toRequestBody(MultipartBody.FORM)
        val email = body.email.toRequestBody(MultipartBody.FORM)

        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePath = MultipartBody.Part.createFormData("profile_pic", file.name, requestFile)

        return getApi.updateProfile(tippingName,filePath,firstName,email,surname,gender,dob,country)
    }

    suspend fun joinComp(body:JoinCompModel):Response<ResponseJoinComp>{
        return getApi.joinComp(body)
    }

    suspend fun sportsList(username:String,token:String,tz:String):Response<ResponseBody>{
       return RetrofitClient2().getApi().sportsList(username,token,tz)
    }
    suspend fun getEventsDaily(username:String,token:String,tz: String,sportsFk:String,languageFk:String,date:String):Response<ResponseBody>{
        return RetrofitClient2().getApi().getDailyEvents(username,token,tz,sportsFk,languageFk,date)
    }
    suspend fun submitTips(body:SubmitTipModel):Response<ResponseSubmitTips>{
        return getApi.submitTips(body)
    }

    suspend fun resetPassword(body:ChangePasswordModel):Response<ResponseChangePassword>{
        return getApi.resetPassword(body)
    }
    suspend fun logout():Response<ResponseLogout>{
        return getApi.logout()
    }


    suspend fun getTimezone():Response<ResponseTimezone>{
        return getApi.getTimezone()
    }

    suspend fun getDailyMatches():Response<ResponseOdds>{
        return RetrofitClient2().getApi().getMatches(11686,2022,"afl1","WiZardTips22","W1z4rdt1p5twotwo","")
    }

    suspend fun getMatchesRl():Response<ResponseRugbyLeague>{
            return RetrofitClient2().getApi().getMatchesRl(3,2022,"RL1","WiZardTips22","W1z4rdt1p5twotwo","")
    }

}