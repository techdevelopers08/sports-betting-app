package com.app.sportbetting.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sportbetting.models.createComp.CreateCompModel
import com.app.sportbetting.models.createComp.ResponseCreateComp
import com.app.sportbetting.models.joinComp.JoinCompModel
import com.app.sportbetting.models.joinComp.ResponseJoinComp
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCompViewModel(val application: Application, val repository: Repository): ViewModel() {

    val createCompResult = MutableLiveData<Resource<ResponseCreateComp>>()
    val joinCompResult = MutableLiveData<Resource<ResponseJoinComp>>()

    fun createComp(body:CreateCompModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createComp(body)
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                createCompResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                createCompResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            createCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            createCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            createCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            createCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        createCompResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        createCompResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        createCompResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }

    fun joinComp(body:JoinCompModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.joinComp(body)
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                joinCompResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                joinCompResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            joinCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            joinCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            joinCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            joinCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        joinCompResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        joinCompResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        joinCompResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }

}