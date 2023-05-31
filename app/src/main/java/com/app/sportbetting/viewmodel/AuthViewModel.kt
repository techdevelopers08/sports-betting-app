package com.app.sportbetting.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sportbetting.models.changePassword.ChangePasswordModel
import com.app.sportbetting.models.changePassword.ResponseChangePassword
import com.app.sportbetting.network.Repository
import com.app.sportbetting.models.login.LoginDetailsModel
import com.app.sportbetting.models.login.ResponseLogin
import com.app.sportbetting.models.logout.ResponseLogout
import com.app.sportbetting.models.register.RegisterModel
import com.app.sportbetting.models.register.ResponseRegistration
import com.app.sportbetting.models.timezone.ResponseTimezone
import com.app.sportbetting.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(val application: Application, val repository: Repository):ViewModel() {
    val registerResult = MutableLiveData<Resource<ResponseRegistration>>()
    val loginResult = MutableLiveData<Resource<ResponseLogin>>()
    val logoutResult = MutableLiveData<Resource<ResponseLogout>>()

    val resetPassword = MutableLiveData<Resource<ResponseChangePassword>>()
    val timezoneResult = MutableLiveData<Resource<ResponseTimezone>>()

    fun onRegister(body: RegisterModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.onRegister(body)
                Log.d("TAG", "onRegister: $response")
                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                registerResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                registerResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            registerResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            registerResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            registerResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            registerResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        registerResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )


                    } else if (response.code() == 400) {

                        registerResult.value = Resource.error(
                            data = null,
                            message = "email must be unique"
                        )
                    } else {

                        registerResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onRegister: $t")
            }
        }


    }

    fun onLogin(body: LoginDetailsModel) {
        loginResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.onLogin(body)

                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                loginResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                loginResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            loginResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }else if (response.body()?.code == 400) {

                            loginResult.value = Resource.error(
                                data = response.body()!!,
                                message = "Incorrect Email Address or Password"
                            )

                        }
                        else if (response.body()?.code == 401) {

                            loginResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else {

                            loginResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        loginResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )

                    } else if (response.code() == 400) {

                        loginResult.value =
                            Resource.error(
                                data = null,
                                message = "Incorrect Email Address or Password"
                            )

                    } else {

                        loginResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "onLogin: ")
            }
        }

    }

    fun logout() {
        logoutResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.logout()

                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                logoutResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                logoutResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            logoutResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            logoutResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            logoutResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            logoutResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        logoutResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )
                    } else if (response.code() == 400) {

                        logoutResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )

                    } else {

                        logoutResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "logout: $t")
            }
        }

    }

    fun resetPassword(body:ChangePasswordModel) {
        resetPassword.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.resetPassword(body)

                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                resetPassword.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                resetPassword.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            resetPassword.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            resetPassword.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            resetPassword.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            resetPassword.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        resetPassword.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )
                    } else if (response.code() == 400) {

                        resetPassword.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )

                    } else {

                        resetPassword.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "logout: $t")
            }
        }

    }

    fun getTimezone() {
        timezoneResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getTimezone()

                withContext(Dispatchers.Main) {
                    if (response.code() == 200 && response.isSuccessful) {

                        if (response.body()?.code == 200) {

                            if (response.body() != null) {

                                timezoneResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            } else {

                                timezoneResult.value = Resource.success(
                                    data = response.body()!!,
                                    message = response.body()?.message.toString()
                                )

                            }
                        } else if (response.body()?.code == 204) {

                            timezoneResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        } else if (response.body()?.code == 401) {

                            timezoneResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )


                        } else if (response.body()?.code == 400) {

                            timezoneResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )
                        } else {

                            timezoneResult.value = Resource.error(
                                data = response.body()!!,
                                message = response.body()?.message.toString()
                            )

                        }

                    } else if (response.code() == 204) {

                        timezoneResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )
                    } else if (response.code() == 400) {

                        timezoneResult.value =
                            Resource.error(
                                data = null,
                                message = response.body()?.message.toString()
                            )

                    } else {

                        timezoneResult.value =
                            Resource.error(data = null, message = "Something went wrong")

                    }
                }
            } catch (t: Throwable) {
                Log.d("TAG", "logout: $t")
            }
        }

    }



}