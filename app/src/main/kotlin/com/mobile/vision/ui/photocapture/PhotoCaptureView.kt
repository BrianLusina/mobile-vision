package com.mobile.vision.ui.photocapture

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

    fun processAndSetImage()

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
     * Resamples the given picture given the photo path
     * @param photoPath
     * */
    fun resamplePic(photoPath: String)
}