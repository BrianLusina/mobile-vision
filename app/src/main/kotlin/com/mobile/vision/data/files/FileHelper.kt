package com.mobile.vision.data.files

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

/**
 * @author lusinabrian on 06/11/17.
 * @Notes File helper interface to handle file actions
 */
interface FileHelper {

    /**
     * Deletes an image file given the photo Path, returns True if deletion is successfull, false
     * otherwise
     * @param photoPath Path for the image
     * @return [Boolean]
     * */
    fun deleteImageFile(photoPath : String) : Boolean

    /**
     * Saves the image file and sets the destination
     * @param mResultsBitmap Bitmap of the given photo
     * @return [String] Location of the image saved
     * */
    fun saveImageFile(mResultsBitmap: Bitmap?) : String?

    /**
     * Creates a temporary image file
     * */
    fun createTempImageFile() : Pair<File?, Uri>
}