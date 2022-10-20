package com.sagara.klipz.network.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.sagara.klipz.BuildConfig
import com.sagara.klipz.GlobalApp
import com.sagara.klipz.network.interceptor.NetworkConnectionInterceptor
import com.sagara.klipz.network.interceptor.TokenAuthenticator
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
        if (!refreshTokenStatus) {
            httpClient.addInterceptor(interceptor)
            httpClient.authenticator(TokenAuthenticator(baseUrl))
        }

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            httpClient.addInterceptor(ChuckerInterceptor(GlobalApp.getAppContext()))
        }

        /*add timeout*/
        httpClient.callTimeout(120, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}
