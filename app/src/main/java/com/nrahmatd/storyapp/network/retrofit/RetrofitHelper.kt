package com.nrahmatd.storyapp.network.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.nrahmatd.storyapp.BuildConfig
import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.network.interceptor.NetworkConnectionInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun invoke(baseUrl: String, interceptor: NetworkConnectionInterceptor, refreshTokenStatus: Boolean): Retrofit {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val httpClient = OkHttpClient.Builder()
        httpClient.dispatcher(dispatcher)
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            httpClient.addInterceptor(ChuckerInterceptor(GlobalApp.getAppContext()))
        }

        /** Add Timeout */
        httpClient.callTimeout(120, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}
