package com.mobile.vision.ui.photocapture

import android.net.Uri
import com.mobile.vision.data.events.ImageUploadEvent
import com.mobile.vision.ui.base.BaseView

/**
 * @author lusinabrian on 20/09/17.
 * @Notes view for main activity
 */
interface PhotoCaptureView : BaseView {

    /**
     * sets up listeners
     * */
    fun setupListeners()

    /**
     * Make views visible
     * This takes in a boolean value, that we can use to either make views visible or not
     * @param imageAvailable If set to True, the pick image and take photo buttons are made INVISIBLE
     * otherwise the image is made available
     * */
    fun makeViewsVisible(imageAvailable: Boolean)

    /**
     * Launches the device's camera
     * */
    fun launchCamera()

    /**
     * Take picture function that is triggered when the user requests to take a picture
     * */
    fun takePicture()

    /**
     * Displays permission rationale
     * This is a simple dialog displaying why we need certain permissions to proceed with the
     * request
     * */
    fun displayPermissionRationale()

    /**
     * Processes and sets the captured image
     * */
    fun processAndSetCapturedImage()

    /**
     * Process and sets the image from the given photoPathUri that has been picked from gallery
     * */
    fun processAndSetPickedImage(photoPathUri: Uri?)

    fun displayImageUploadProgressBar()

    fun displayImageUploadFailure()

    /**
     * Opens users Gallery for them to pick a picture
     * */
    fun chooseImageFromGallery()

    /**
     * Notify user of saved photo location
     * @param savedPhotoLocation
     * */
    fun notifyUserOfSavedImage(savedPhotoLocation: String?)

    /**
     * Clears views and image from View
     * @param isFileDeleted Whether the file was successfully deleted
     * */
    fun clearImage(isFileDeleted: Boolean)

    /**
     * Re-samples the given picture given the photo path
     * @param photoPath
     * */
    fun resampleCapturedImage(photoPath: String)

    /**
     * Re-sample the picked image from gallery given the photoPath uri
     * */
    fun resamplePickedImage(photoPathUri: Uri?)

    /**
     * Subscriber method for the Image Upload event
     * @param imageUploadEvent Image Upload Event from Event Bus
     * */
    fun onReceiveUploadImageEvent(imageUploadEvent: ImageUploadEvent)
}