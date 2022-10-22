package com.nrahmatd.storyapp.authentication.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("loginResult")
    val loginResult: LoginResult? = null,
    @SerializedName("message")
    val message: String
) {
    data class LoginResult(
        @SerializedName("name")
        val name: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("userId")
        val userId: String
    )
}
