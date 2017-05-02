package framework.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
object SPUtils {

    private val SHAREDPREFERENCES_NAME = "example"
    private val THEME = "theme"
    private var sharedPreferences: SharedPreferences? = null

    private fun initSharePreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun init(context: Context) {
        initSharePreferences(context)
    }

    val isNull: Boolean
        get() = sharedPreferences == null


    fun isTheme(defaultValue: Boolean): Boolean {
        if (isNull) {
            return defaultValue
        }
        return sharedPreferences!!.getBoolean(THEME, defaultValue)
    }

    fun setTheme(value: Boolean) {
        if (isNull) {
            return
        }
        sharedPreferences!!.edit().putBoolean(THEME, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        if (isNull) {
            return defaultValue
        }
        return sharedPreferences!!.getString(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        if (isNull) {
            return
        }
        sharedPreferences!!.edit().putString(key, value).apply()
    }

    fun clearAll() {
        if (isNull) {
            return
        }
        sharedPreferences!!.edit().clear().apply()
    }

}
