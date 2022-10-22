package com.nrahmatd.storyapp.app

import android.app.Application
import android.content.Context
import android.content.Intent
import com.nrahmatd.storyapp.BuildConfig
import com.nrahmatd.storyapp.authentication.view.AuthenticationActivity
import com.nrahmatd.storyapp.database.sharedpref.PreferenceManager
import com.nrahmatd.storyapp.utils.CustomCrashHandler

class GlobalApp : Application() {

    companion object {
        private lateinit var mAppContext: Context

        @get:Synchronized
        lateinit var instance: GlobalApp

        private fun setAppContext(ctx: Context) {
            mAppContext = ctx
        }

        fun getAppContext(): Context {
            return mAppContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        setAppContext(this)
        instance = this
        setupChuckerErrors()
    }

    private fun setupChuckerErrors() {
        if (BuildConfig.DEBUG) {
            val systemHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(CustomCrashHandler(systemHandler, this))
        }
    }

    fun logout() {
        PreferenceManager.instance.sharedPref.edit().clear().apply()
        startActivity(
            Intent(
                getAppContext(),
                AuthenticationActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
