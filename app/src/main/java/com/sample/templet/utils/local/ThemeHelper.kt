package com.sample.templet.utils.local

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sample.templet.R
import com.sample.templet.utils.AppPreferences

class ThemeHelper {
    private var defaultTheme: String? = "default"

    companion object {
        private var themeHelper: ThemeHelper? = null

        @Synchronized
        fun getInstance(): ThemeHelper? {
            if (themeHelper == null) {
                themeHelper = ThemeHelper()
            }
            return themeHelper
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getCurrentTheme(context: Context): Int {
        return getTheme(context)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTheme(context: Context): Int {
        return when (AppPreferences.getString(AppPreferences.APP_THEME_NAME, defaultTheme)) {
            "default" -> context.resources.getColor(R.color.default_orange, null)
            "veniceblue" -> context.resources.getColor(R.color.veniceblue, null)
            "facebook" -> context.resources.getColor(R.color.facebook, null)
            "navy" -> context.resources.getColor(R.color.navy, null)
            "turquoise" -> context.resources.getColor(R.color.turquoise, null)
            "Dark" -> context.resources.getColor(R.color.dark, null)
            else -> context.resources.getColor(R.color.default_orange, null)
        }
    }
}