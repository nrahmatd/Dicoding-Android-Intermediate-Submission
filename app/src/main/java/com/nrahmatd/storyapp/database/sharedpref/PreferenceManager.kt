package com.nrahmatd.storyapp.database.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.app.GlobalApp

class PreferenceManager private constructor() {

    companion object {
        val instance: PreferenceManager by lazy { Initiate.inst }
    }

    val sharedPref: SharedPreferences = GlobalApp.getAppContext().getSharedPreferences(
        GlobalApp.getAppContext().getString(
            R.string.app_name
        ), Context.MODE_PRIVATE
    )

    private object Initiate {
        val inst = PreferenceManager()
    }

    fun putString(key: String, value: String?) {
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPref.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        with(sharedPref.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPref.edit().putBoolean(key, value).apply()
    }

    fun getBool(key: String, defaultValue: Boolean): Boolean? {
        return sharedPref.getBoolean(key, defaultValue)
    }
}
