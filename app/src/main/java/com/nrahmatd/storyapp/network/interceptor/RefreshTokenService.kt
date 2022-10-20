package com.sagara.klipz.network.interceptor

import com.google.gson.JsonObject
import com.sagara.klipz.auth.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RefreshTokenService {
    @Headers("Accept: application/vnd.api+json", "Content-Type: application/vnd.api+json")
    @POST("auth/refresh-token")
    fun refreshToken(@Body body: JsonObject): Call<User>
}
