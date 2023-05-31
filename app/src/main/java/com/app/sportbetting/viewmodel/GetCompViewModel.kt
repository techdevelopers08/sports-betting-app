package com.app.sportbetting.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sportbetting.models.getComp.ResponseGetCompList
import com.app.sportbetting.models.getModels.hideLadder.ResponseHideLadderList
import com.app.sportbetting.models.getModels.submissionList.ResponseSubmissionList
import com.app.sportbetting.models.getModels.tipDraw.ResponseTipDrawList
import com.app.sportbetting.network.Repository
import com.app.sportbetting.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetCompViewModel(val application: Application, val repository: Repository): ViewModel()  {
    val tipDrawListResult = MutableLiveData<Resource<ResponseTipDrawList>>()
    val submissionListResult = MutableLiveData<Resource<ResponseSubmissionList>>()
    val hideLadderListResult = MutableLiveData<Resource<ResponseHideLadderList>>()
    val getCompResult = MutableLiveData<Resource<ResponseGetCompList>>()

    fun submissionList() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.submissionList()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                submissionListResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                submissionListResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            submissionListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            submissionListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            submissionListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            submissionListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        submissionListResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        submissionListResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        submissionListResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }
    fun tipDrawlist() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.tipDrawList()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                tipDrawListResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                tipDrawListResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            tipDrawListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            tipDrawListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            tipDrawListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            tipDrawListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        tipDrawListResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        tipDrawListResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        tipDrawListResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }


    }

    fun ladderList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.hideLadderList()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                hideLadderListResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                hideLadderListResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            hideLadderListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            hideLadderListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            hideLadderListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            hideLadderListResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        hideLadderListResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        hideLadderListResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        hideLadderListResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }


    }


    fun getCompList() {
        getCompResult.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCompList()
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                getCompResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                getCompResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            getCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            getCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            getCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            getCompResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        getCompResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        getCompResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        getCompResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }

    }


}