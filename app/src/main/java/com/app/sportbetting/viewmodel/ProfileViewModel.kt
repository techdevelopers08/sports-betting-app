package com.app.sportbetting.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sportbetting.models.profile.getProfile.ResponseGetProfile
import com.app.sportbetting.models.profile.getProfile.updateProfile.ProfileParamModel
import com.app.sportbetting.models.profile.getProfile.updateProfile.ResponseUpdateProfile
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ProfileViewModel(val application: Application, val repository: Repository): ViewModel()  {
    val getProfileResult = MutableLiveData<Resource<ResponseGetProfile>>()
    val updateProfileResult = MutableLiveData<Resource<ResponseUpdateProfile>>()


    fun getProfile() {
        getProfileResult.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = repository.getProfile()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                getProfileResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                getProfileResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            getProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            getProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            getProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            getProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        getProfileResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        getProfileResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        getProfileResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }

    fun updateProfile(body:ProfileParamModel,file:File) {
        updateProfileResult.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateProfile(body,file)
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                updateProfileResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                updateProfileResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            updateProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            updateProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            updateProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            updateProfileResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        updateProfileResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        updateProfileResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        updateProfileResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }

}