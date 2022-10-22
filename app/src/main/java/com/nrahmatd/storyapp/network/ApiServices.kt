package com.nrahmatd.storyapp.network

import com.nrahmatd.storyapp.authentication.model.LoginModel
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.utils.general_model.GeneralModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServices {

    /** Authentication **/

    /**
     * Login
     * @param email User's email
     * @param password User's password
     */
    @FormUrlEncoded
    @POST(Api.LOGIN)
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginModel>

    /**
     * Register
     * @param name User's name
     * @param email User's email
     * @param password User's password
     */
    @FormUrlEncoded
    @POST(Api.REGISTER)
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<GeneralModel>

    /**
     * All Stories
     * @param token User's authentication token
     * @param page
     * @param size
     */
    @GET(Api.STORIES)
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): Response<AllStoriesModel>

    /**
     * Upload Image
     * @param token User's authentication token
     * @param file Multipart image
     * @param description Image description
     * @param lat Latitude
     * @param lon Longitude
     */
    @Multipart
    @POST(Api.STORIES)
    suspend fun createStories(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Response<GeneralModel>
}
