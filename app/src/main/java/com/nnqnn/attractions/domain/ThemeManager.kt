package com.nnqnn.attractions.domain

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class ThemeManager(context: Context) {
    companion object {
        private const val KEY_DARK = "pref_dark_mode"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun isDark(): Boolean = prefs.getBoolean(KEY_DARK, false)

    fun toggleDark() {
        val newValue = !isDark()
        prefs.edit().putBoolean(KEY_DARK, newValue).apply()
        applyTheme(newValue)
    }

    fun applyCurrent() = applyTheme(isDark())

    private fun applyTheme(dark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}

