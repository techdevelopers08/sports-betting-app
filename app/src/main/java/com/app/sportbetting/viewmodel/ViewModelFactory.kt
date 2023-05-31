package com.app.sportbetting.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.network.Repository

class ViewModelFactory(
    private val application: Application,
    private val repository: Repository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(application, repository) as T
        }
        else if(modelClass.isAssignableFrom(GetCompViewModel::class.java)){
            return GetCompViewModel(application, repository) as T
        }

        else if(modelClass.isAssignableFrom(CreateCompViewModel::class.java)){
            return CreateCompViewModel(application, repository) as T
        }
        else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(application, repository) as T
        }
        else if(modelClass.isAssignableFrom(SportsViewModel::class.java)){
            return SportsViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
