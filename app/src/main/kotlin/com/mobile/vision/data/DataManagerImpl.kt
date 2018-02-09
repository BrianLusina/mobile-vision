package com.mobile.vision.data

import android.graphics.Bitmap
import android.net.Uri
import com.mobile.vision.data.api.ApiHelper
import com.mobile.vision.data.files.FileHelper
import com.mobile.vision.data.prefs.SharedPrefsHelper
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 23/09/17.
 * @Notes Implementation for the datamanager
 */
@Singleton
class DataManagerImpl
@Inject
constructor(val apiHelper: ApiHelper, val prefsHelper: SharedPrefsHelper, val fileHelper: FileHelper) : DataManager {

    override fun saveImageFilePath(imageFileKey: String, imageFilePath: String) {
        prefsHelper.saveImageFilePath(imageFileKey, imageFilePath)
    }

    override fun getImageFilePath(imageFileKey: String): String {
        return prefsHelper.getImageFilePath(imageFileKey)
    }

    override fun deleteImageFile(photoPath: String): Boolean {
        return fileHelper.deleteImageFile(photoPath)
    }

    override fun saveImageFile(mResultsBitmap: Bitmap?): String? {
        return fileHelper.saveImageFile(mResultsBitmap)
    }

    override fun createTempImageFile(): Pair<File?, Uri> {
        return fileHelper.createTempImageFile()
    }

    override fun uploadImageFile(filePath: String) {
        apiHelper.uploadImageFile(filePath)
    }

    override fun retrieveVisionApiData() {
    }

    override fun parseVisionApiResponse() {
    }
}