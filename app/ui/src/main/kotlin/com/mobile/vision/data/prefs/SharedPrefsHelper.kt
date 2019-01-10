package com.mobile.vision.data.prefs

/**
 * @author lusinabrian on 07/11/17.
 * @Notes Shared Prefs helper
 */
interface SharedPrefsHelper {

    /**
     * Saves image file path to shared preferences
     * @param imageFileKey Key to use to store the image file path
     * @param imageFilePath File path of the image file
     * */
    fun saveImageFilePath(imageFileKey: String, imageFilePath : String)

    /**
     * Retrieves the image file path
     * @param imageFileKey image file key to use to read from shared preferences
     * */
    fun getImageFilePath(imageFileKey : String) : String
}