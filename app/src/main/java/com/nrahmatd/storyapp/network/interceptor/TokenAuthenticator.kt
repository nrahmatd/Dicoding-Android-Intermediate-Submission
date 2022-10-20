package com.sagara.klipz.network.interceptor

import android.os.SystemClock
import com.google.gson.JsonObject
import com.sagara.klipz.GlobalApp
import com.sagara.klipz.database.sharedpref.PreferenceManager
import com.sagara.klipz.network.retrofit.RetrofitHelper
import com.sagara.klipz.utils.GlobalVariable
import com.sagara.klipz.utils.setErrorBody
import java.io.IOException
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val baseUrl: String) : Authenticator {
    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        SystemClock.sleep(1000)
        val accessToken = response.request.header("access_token")
        val localAccessToken = PreferenceManager.instance.getString(GlobalVariable.ACCESS_TOKEN, "")
        val refreshToken = PreferenceManager.instance.getString(GlobalVariable.REFRESH_TOKEN, "")

        if (response.code == 401 && !refreshToken.isNullOrEmpty()) {
            if (accessToken == localAccessToken) {
                if (PreferenceManager.instance.getBool("is_refresh", false) != true) {
                    PreferenceManager.instance.putBoolean("is_refresh", true)
                    val params = JsonObject()
                    params.addProperty(
                        GlobalVariable.REFRESH_TOKEN,
                        PreferenceManager.instance.getString(GlobalVariable.REFRESH_TOKEN, "")
                    )

                    val service: RefreshTokenService =
                        RetrofitHelper.invoke(
                            baseUrl,
                            NetworkConnectionInterceptor(GlobalApp.getAppContext()),
                            true
                        )
                            .create(RefreshTokenService::class.java)

                    val mRequest = service.refreshToken(params)
                    val resp = mRequest.execute()
                    if (resp.isSuccessful) {
                        val token = resp.body()?.data?.accessToken
                        PreferenceManager.instance.putString(
                            GlobalVariable.REFRESH_TOKEN,
                            resp.body()?.data?.refreshToken!!
                        )
                        PreferenceManager.instance.putString(
                            GlobalVariable.ACCESS_TOKEN,
                            token!!
                        )
                        PreferenceManager.instance.putBoolean("is_refresh", false)

                        return response.request.newBuilder()
                            .header(GlobalVariable.ACCESS_TOKEN, token)
                            .build()
                    } else {
                        val error = setErrorBody(resp.errorBody()!!)
                        if (error.code == 404) {
                            PreferenceManager.instance.let {
                                if (!it.getString(GlobalVariable.USER_ID, "").isNullOrEmpty()) {
                                    GlobalApp.instance.logout()
                                }
                            }
                        }
                    }
                } else return null
            } else {
                return response.request.newBuilder()
                    .header("access_token", localAccessToken!!)
                    .build()
            }
        }
        return null
    }
}
