package com.app.sportbetting.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Context.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}


fun View.delayOnLifecycle(duration:Long,dispatcher:CoroutineDispatcher = Dispatchers.Main,
    block : () -> Unit): Job? = findViewTreeLifecycleOwner()?.let {
        it.lifecycle.coroutineScope.launch(dispatcher) {
            delay(duration)
            block()
        }
}




class Utility {

    companion object {

        fun isInternetConnected(context: Context?): Boolean {
            val manager: ConnectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = manager.activeNetworkInfo
            return network != null && network.isConnectedOrConnecting
        }
        fun checkNetwork(): Boolean {
            var isConnected = false
            if (isInternetConnected(MyApplication.getContext())) {
                isConnected = true
            } else {
                isConnected = false
                Toast.makeText(
                    MyApplication.getContext(),
                    "Unable to connect to the internet",
                    Toast.LENGTH_LONG
                ).show()
            }
            return isConnected

        }


        @SuppressLint("SimpleDateFormat")
        fun convertStringToDate(date:String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd-EEE-yyyy , hh:mm")
            val date = inputFormat.parse(date)
            val formattedDate = outputFormat.format(date)
            println(formattedDate)
            return formattedDate
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun getDate(date: String): String {
            val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("EEEE d MMM yyyy", Locale.ENGLISH)
            val dateLocal: LocalDate = LocalDate.parse(date, inputFormatter)
            return outputFormatter.format(dateLocal)// prints 10-04-2018

        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun getDateTime(date: String): String {
            val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS zzz", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("EEEE d MMM yyyy", Locale.ENGLISH)
            val dateLocal: LocalDate = LocalDate.parse(date, inputFormatter)
            return outputFormatter.format(dateLocal)

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getRlTime(date: String): String {
            val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS zzz", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
            val dateLocal: LocalDate = LocalDate.parse(date, inputFormatter)
            return outputFormatter.format(dateLocal)

        }



        @RequiresApi(Build.VERSION_CODES.O)
        fun getTime(date:String): String {
            val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
            val dateLocal: LocalDate = LocalDate.parse(date, inputFormatter)
            return outputFormatter.format(dateLocal)
        }


    }









    suspend fun <T> getFailureMessage(
        t: Throwable,
        result: MutableLiveData<Resource<T>>,
        scope: CoroutineScope
    ) {
        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                when (t) {
                    is UnknownHostException -> {
                        result.value = Resource.error(
                            data = null,
                            message = "Check Your Internet Connection"
                        )
                    }
                    is NoRouteToHostException -> {
                        result.value = Resource.error(
                            data = null,
                            message = "Check Your Internet Connection"
                        )
                    }

                    is ConnectException -> {
                        result.value = Resource.error(
                            data = null,
                            message = "Failed to connect server"
                        )
                    }
                    else -> {
                        result.value = Resource.error(
                            data = null,
                            message = "something went wrong !! please try again"
                        )
                    }
                }
            }
        }
    }
}


