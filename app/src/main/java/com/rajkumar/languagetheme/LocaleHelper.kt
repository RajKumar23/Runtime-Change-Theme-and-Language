package com.rajkumar.languagetheme

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*


object LocaleHelper {

    private lateinit var session: SessionManager

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    fun onAttach(context: Context): Context {
        session = SessionManager(context)
        return setLocale(context, session.getLanguage())
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        session = SessionManager(context)
        return setLocale(context, session.getLanguage())
    }

    fun getLanguage(context: Context): String? {
        session = SessionManager(context)
        return session.getLanguage()
    }

    fun setLocale(context: Context, language: String): Context {
        session = SessionManager(context)
        session.setLanguage(language)
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }


    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}