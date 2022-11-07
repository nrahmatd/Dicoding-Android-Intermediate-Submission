package com.nrahmatd.storyapp.di

import android.content.Context
import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.database.local.StoriesDatabase
import com.nrahmatd.storyapp.network.Api
import com.nrahmatd.storyapp.network.ApiRepository
import com.nrahmatd.storyapp.network.ApiServices
import com.nrahmatd.storyapp.network.interceptor.NetworkConnectionInterceptor
import com.nrahmatd.storyapp.network.retrofit.RetrofitHelper

object Injection {
    fun provideRepository(context: Context): ApiRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = RetrofitHelper.invoke(
            Api.BASE_URL,
            NetworkConnectionInterceptor(GlobalApp.getAppContext()),
            false
        ).create(ApiServices::class.java)
        return ApiRepository(database, apiService)
    }
}
