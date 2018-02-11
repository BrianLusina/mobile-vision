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
     * On Pick image request failed
     * This will handle errors when wecould not get an image from gallery
     * */
    fun onPickImageRequestFailed()

    /**
     * Pick image request success and we take the file path uri and upload it
     * to VisionAPi*/
    fun onPickImageRequestSuccess(photoPathUri: Uri?)

    /**
     * Clears the given image if available
     * */
    fun onClearButtonClicked()

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
    fun onImageCaptureSuccess()

    /**
     * On Activity result failed
     * */
    fun onActivityResultFailed()

    /**
     * Request to resample picture
     * */
    fun onResampleCapturedImageRequest()

    /**
     * Request to resample picked gallery image
     * */
    fun onResamplePickedImageRequest(photoPathUri: Uri?)

}