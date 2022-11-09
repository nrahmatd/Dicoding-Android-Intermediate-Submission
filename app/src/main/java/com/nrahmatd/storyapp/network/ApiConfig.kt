package com.nrahmatd.storyapp.network

import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.network.interceptor.NetworkConnectionInterceptor
import com.nrahmatd.storyapp.network.retrofit.RetrofitHelper

class ApiConfig {
    companion object {
        var API_BASE_URL_MOCK: String? = null
        fun getApiServices(): ApiServices {
            return RetrofitHelper.invoke(
                API_BASE_URL_MOCK ?: Api.BASE_URL,
                NetworkConnectionInterceptor(GlobalApp.getAppContext()),
                false
            ).create(ApiServices::class.java)
        }
    }
}
