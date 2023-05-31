package com.app.sportbetting.network

import android.util.Log
import androidx.viewbinding.BuildConfig
import com.app.sportbetting.utils.Constants
import com.app.sportbetting.utils.MyApplication
import com.app.sportbetting.utils.SharedPreference

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        var retrofit: Retrofit? = null
        var client: OkHttpClient? = null

        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .callTimeout(40, TimeUnit.MINUTES)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(provideHttpLoggingInterceptor())

        fun setupClient() {
            httpClient.addInterceptor(Interceptor { chain ->
                val request: Request
                val token = SharedPreference.getInstance(MyApplication.getContext())
                    .getString(SharedPreference.Key.TOKEN, "null")
                println("token $token")
                request = if (!token.isNullOrEmpty()) {
                    chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "text/plain")
                        .addHeader("Authorization", "Bearer $token").build()       //Bearer $token
                } else {
                    chain.request().newBuilder()
                        .build()
                }

                chain.proceed(request)
            })
        }

        private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor { message -> Log.d("injector", message) }
            httpLoggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            return httpLoggingInterceptor
        }
    }

    fun getApi(): ApiInterface {
        return retrofit?.create(ApiInterface::class.java)!!
    }

    init {
        setupClient()
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}