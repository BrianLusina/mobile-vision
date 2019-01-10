package com.mobile.vision.data.files

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import com.mobile.vision.di.qualifier.AppCtxQualifier
import com.mobile.vision.utils.FILE_PROVIDER_AUTHORITY
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Implementation for the file helper
 */
@Singleton
class FileHelperImpl @Inject constructor(@AppCtxQualifier val context: Context): FileHelper {

    override fun deleteImageFile(photoPath: String): Boolean {
        // Get the file
        val imageFile = File(photoPath)

        // Delete the image
        return imageFile.delete()
    }

    override fun saveImageFile(mResultsBitmap: Bitmap?): String? {
        var savedImagePath: String? = null

        // Create the new file in the external storage
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_$timeStamp.jpg"
        val storageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString() + "/EmojifyMe")
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }

        // Save the new Bitmap
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                mResultsBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
            galleryAddPic(context, savedImagePath)
        }

        return savedImagePath
    }

    override fun createTempImageFile() : Pair<File?, Uri>{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.externalCacheDir

        val photoFile = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        val photoUri = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, photoFile)

        return Pair(photoFile, photoUri)
    }


    /**
     * Helper method for adding the photo to the system photo gallery so it can be accessed
     * from other apps.
     *
     * @param imagePath The path of the saved image
     */
    private fun galleryAddPic(context: Context, imagePath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }
}