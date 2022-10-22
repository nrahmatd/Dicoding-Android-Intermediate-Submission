package com.nrahmatd.storyapp.network

import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.database.sharedpref.PreferenceManager
import com.nrahmatd.storyapp.network.interceptor.NetworkConnectionInterceptor
import com.nrahmatd.storyapp.network.retrofit.RetrofitHelper
import com.nrahmatd.storyapp.utils.GlobalVariable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiRepository {
    private var apiServices: ApiServices =
        RetrofitHelper.invoke(
            Api.BASE_URL,
            NetworkConnectionInterceptor(GlobalApp.getAppContext()),
            false
        ).create(ApiServices::class.java)

    private val accessToken =
        "Bearer ".plus(PreferenceManager.instance.getString(GlobalVariable.ACCESS_TOKEN, ""))

    /** Authentication **/
    suspend fun login(email: String, password: String) = apiServices.login(email, password)
    suspend fun register(name: String, email: String, password: String) =
        apiServices.register(name, email, password)

    /** Stories **/
    suspend fun getAllStories() =
        apiServices.getAllStories(accessToken, null, null, null)

    suspend fun createStories(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        long: RequestBody?
    ) =
        apiServices.createStories(accessToken, file, description, lat, long)
}
