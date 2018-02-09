package com.mobile.vision.data.api

/**
 * @author lusinabrian on 17/01/18.
 * @Notes Api Helper
 */
interface ApiHelper {

    /**
     * Uploads image file to firebase Storage*/
    fun uploadImageFile(filePath : String)

    /**
     * Retrieves data from the Vision API
     * */
    fun retrieveVisionApiData()

    fun parseVisionApiResponse()
}