package com.mobile.vision.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.mobile.vision.di.qualifier.AppCtxQualifier
import com.mobile.vision.di.qualifier.PreferenceInfo
import javax.inject.Inject

/**
 * @author lusinabrian on 07/11/17.
 * @Notes Prefs helper implementation
 */
class SharedPrefsHelperImpl
@Inject
constructor(@AppCtxQualifier val context: Context,
            @PreferenceInfo val prefFileName: String): SharedPrefsHelper {

    /**
     * Saves data to shared preferences
     * this is an extension function that
     * @param key key to use to save data
     * @param value the value to use to save data
     * extension functions that allows the saving of data to a shared preference file
     * */
    private fun <T> SharedPreferences.saveData(key: String, value: T) {
        when (value) {
            is String -> this.edit().putString(key, value.toString()).apply()
            is Boolean -> this.edit().putBoolean(key, value).apply()
            is Int -> this.edit().putInt(key, value.toInt()).apply()
            is Float -> this.edit().putFloat(key, value.toFloat()).apply()
            is Long -> this.edit().putLong(key, value.toLong()).apply()
        }
    }

    private val mSharedPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun saveImageFilePath(imageFileKey: String, imageFilePath: String) {
        mSharedPrefs.saveData(imageFileKey, imageFilePath)
    }

    override fun getImageFilePath(imageFileKey: String): String {
        return mSharedPrefs.getString(imageFileKey, "")
    }
}