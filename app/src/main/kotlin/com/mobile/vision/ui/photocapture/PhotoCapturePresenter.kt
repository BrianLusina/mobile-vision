package com.mobile.vision.ui.photocapture

import android.net.Uri
import android.os.Bundle
import com.mobile.vision.ui.base.BasePresenter

/**
 * @author lusinabrian on 20/09/17.
 * @Notes Presenter layer
 */
interface PhotoCapturePresenter<V : PhotoCaptureView> : BasePresenter<V> {

    /**
     * on Start
     * */
    fun onStart()

    /**
     * ON Resume
     * */
    fun onResume()

    fun onViewCreated(savedInstanceState: Bundle?)

    /**
     * callback for when the Take picture button is clicked
     * */
    fun onTakePictureButtonClicked()

    /**
     * On upload picture button clicked. weill upload the picture to
     * Cloud Vision API
     * */
    fun onUploadPictureButtonClicked()

    /**
     * On Picking a picture From gallery for upload
     * */
    fun onPickPictureButtonClicked()

    /**
     * On permission granted callback we can proceed with the action that required user permission
     * */
    fun onPermissionGranted()

    /**
     * On permission denied callback that is used to launch a Rationale dialog
     * as to why permissions are being requested
     * */
    fun onPermissionDenied()

    fun onTakePicture() : Uri?

    /**
     * On Activity result success
     * */
    fun onActivityResultSuccess()

    /**
     * On Activity result failed
     * */
    fun onActivityResultFailed()

    /**
     * Request to resample picture
     * */
    fun onResamplePicRequest()

}