package com.mobile.vision.ui.photocapture

import android.net.Uri
import android.os.Bundle
import com.chatbot.data.io.SchedulerProvider
import com.mobile.vision.ui.base.BasePresenterImpl
import com.mobile.vision.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 20/09/17.
 * @Notes presenter implementation
 */
class PhotoCapturePresenterImpl<V : PhotoCaptureView>
@Inject
constructor(mDataManager: DataManager,
            mSchedulerProvider: SchedulerProvider,
            mCompositeDisposable: CompositeDisposable)
    : PhotoCapturePresenter<V>, BasePresenterImpl<V>(mDataManager, mSchedulerProvider,
        mCompositeDisposable) {

    private val sharedPrefsKey = "mobile_vision"

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
        baseView.setupListeners()
    }

    override fun onPickImageRequestSuccess(filePath: Uri?) {
        // store the filepath to shared preference file
        filePath?.toString()?.let { dataManager.saveImageFilePath(sharedPrefsKey, it) }
        baseView.processAndSetImage()
    }

    override fun onPickPictureButtonClicked() {
        baseView.chooseImageFromGallery()
    }

    override fun onPickImageRequestFailed() {
        baseView.makeViewsVisible(false)
    }

    override fun onUploadPictureButtonClicked() {
        // upload given picture to vision API

    }

    override fun onTakePictureButtonClicked() {
        baseView.takePicture()
    }

    override fun onPermissionDenied() {
        baseView.displayPermissionRationale()
    }

    override fun onPermissionGranted() {
        baseView.launchCamera()
    }

    override fun onTakePicture(): Uri? {
        val (tempImageFile, photoUri)= dataManager.createTempImageFile()
        tempImageFile?.absolutePath?.let { dataManager.saveImageFilePath(sharedPrefsKey, it) }
        return photoUri
    }

    override fun onClearButtonClicked() {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)
        val isFileDeleted = dataManager.deleteImageFile(photoPath)
        baseView.clearImage(isFileDeleted)
    }

    override fun onActivityResultSuccess() {
        baseView.processAndSetImage()
    }

    override fun onActivityResultFailed() {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)
        dataManager.deleteImageFile(photoPath)
    }

    override fun onResamplePicRequest() {
        val photoPath = dataManager.getImageFilePath(sharedPrefsKey)
        baseView.resamplePic(photoPath)
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.dispose()
    }
}