package com.rajkumar.languagetheme

import android.content.Context
import android.content.SharedPreferences
import java.util.*


class SessionManager(context: Context) {
    private var mSharedPreferenceMode = 0
    private var stringSharedPreferenceDevice = "DevicePrf"
    private var mSharedPreferenceDevice: SharedPreferences =
        context.getSharedPreferences(stringSharedPreferenceDevice, mSharedPreferenceMode)
    private var deviceEditor = mSharedPreferenceDevice.edit()

    private val displayMode = "displayMode"
    private val language = "language"

    fun setDisplayMode(displayMode: String) {
        deviceEditor.apply {
            putString(this@SessionManager.displayMode, displayMode)
            apply()
        }
    }

    fun getDisplayMode() = mSharedPreferenceDevice.getString(displayMode, "light")!!

    fun setLanguage(language: String) {
        deviceEditor.apply {
            putString(this@SessionManager.language, language)
            apply()
        }
    }

    fun getLanguage() = mSharedPreferenceDevice.getString(language, "en")!!
}