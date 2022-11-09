package com.nrahmatd.storyapp.di

import android.content.Context
import com.nrahmatd.storyapp.database.local.StoriesDatabase
import com.nrahmatd.storyapp.network.ApiConfig
import com.nrahmatd.storyapp.network.ApiRepository

object Injection {
    fun provideRepository(context: Context): ApiRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiServices()
        return ApiRepository(database, apiService)
    }
}
