package com.mobile.vision.ui.photocapture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.mobile.vision.R
import com.mobile.vision.ui.base.BaseActivity
import com.mobile.vision.utils.resamplePicUtil
import kotlinx.android.synthetic.main.activity_pic_capture.*
import org.jetbrains.anko.error
import org.jetbrains.anko.toast
import java.io.IOException
import javax.inject.Inject


class PhotoCaptureActivity : BaseActivity(), PhotoCaptureView, View.OnClickListener {

    private var mResultsBitmap: Bitmap? = null
    private var filePath: Uri? = null

    private val requestImageCapture = 1
    private val requestStoragePermission = 1
    private val pickImageRequest = 12345

    @Inject
    lateinit var photoCapturePresenter: PhotoCapturePresenter<PhotoCaptureView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_capture)

        activityComponent.injectPhotoCaptureActivity(this)

        photoCapturePresenter.onAttach(this)
    }

    override fun onStart() {
        super.onStart()
        photoCapturePresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        photoCapturePresenter.onResume()
    }

    override fun onStop() {
        photoCapturePresenter.onDetach()
        super.onStop()
    }

    override fun onDestroy() {
        photoCapturePresenter.onDetach()
        super.onDestroy()
    }

    override fun setupListeners() {
        button_pick_picture.setOnClickListener(this)
        button_take_picture.setOnClickListener(this)
        button_upload_picture.setOnClickListener(this)
        fab_clear.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            button_pick_picture -> {
                // pick picture from gallery
                photoCapturePresenter.onPickPictureButtonClicked()
            }

            button_take_picture -> {
                // take picture with camera
                photoCapturePresenter.onTakePictureButtonClicked()
            }

            button_upload_picture -> {
                // upload given picture to Vision API

            }

            fab_clear -> {
                photoCapturePresenter.onClearButtonClicked()
            }
        }
    }

    override fun chooseImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pick a photo"), pickImageRequest)
    }

    override fun takePicture() {
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // If you do not have permission, request it
            requestPermissionsSafely(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestStoragePermission)
        } else {
            // Launch the camera if the permission exists
            launchCamera()
        }
    }

    override fun launchCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // ensure that there is a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoUri = photoCapturePresenter.onTakePicture()
            if (photoUri != null) {
                // store the picture so the camera can save the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                // launch camera activity
                startActivityForResult(takePictureIntent, requestImageCapture)
            } else {
                toast("Could not launch camera")
            }
        } else {
            toast("Your Device does not support taking pictures")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    requestImageCapture -> {
                        // process the image and set it to the image view
                        photoCapturePresenter.onActivityResultSuccess()
                    }
                    pickImageRequest -> {
                        if (data != null) {
                            filePath = data.data
                            try {
                                filePath = checkNotNull(filePath)
                                try {
                                    photoCapturePresenter.onPickImageRequestSuccess(filePath)
                                } catch (ioe: IOException) {
                                    error("Error Retrieving Image with error ${ioe.message}")
                                    // todo: log to external service
                                    photoCapturePresenter.onPickImageRequestFailed()
                                }
                            } catch (ise: IllegalStateException) {
                                error("Failed to load image with error ${ise.message}", ise)
                                toast("Could not load image")
                                photoCapturePresenter.onPickImageRequestFailed()
                            }
                        }
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                // otherwise delete the temporary image
                photoCapturePresenter.onActivityResultFailed()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        // Called when you request permission to read and write to external storage
        when (requestCode) {
            requestStoragePermission -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    photoCapturePresenter.onPermissionGranted()
                } else {
                    photoCapturePresenter.onPermissionDenied()
                }
            }
        }
    }

    override fun makeViewsVisible(imageAvailable: Boolean) {
        if (imageAvailable) {
            // make views visible
            coordinator_image_container.visibility = View.GONE
            // image_view.visibility = View.VISIBLE
            // fab_clear.visibility = View.VISIBLE
            button_upload_picture.visibility = View.VISIBLE

            // hide views
            button_take_picture.visibility = View.GONE
            button_pick_picture.visibility = View.GONE
        } else {
            // Clear the image and toggle the view visibility
            coordinator_image_container.visibility = View.GONE
            // image_view.visibility = View.GONE
            // fab_clear.visibility = View.GONE

            button_pick_picture.visibility = View.VISIBLE
            button_take_picture.visibility = View.VISIBLE
            button_upload_picture.visibility = View.GONE
        }
    }

    override fun processAndSetImage() {
        makeViewsVisible(true)
        photoCapturePresenter.onResamplePicRequest()
    }

    override fun resamplePic(photoPath: String) {
        mResultsBitmap = resamplePicUtil(this, photoPath)
        image_view.setImageBitmap(mResultsBitmap)
    }

    override fun clearImage(isFileDeleted: Boolean) {
        makeViewsVisible(false)

        // If there is an error deleting the file, show a Toast
        if (!isFileDeleted) {
            toast(R.string.error_delete_failure)
        }
    }

    override fun notifyUserOfSavedImage(savedPhotoLocation: String?) {
        if (savedPhotoLocation != null) {
            // Show a Toast with the save location
            toast(getString(R.string.msg_image_saved_location, savedPhotoLocation))
        } else {
            toast(R.string.error_image_not_saved)
        }
    }

    override fun displayPermissionRationale() {
        // dialog with Taionale as to why we need this permission
        // todo
        toast(R.string.msg_permission_denied)
    }
}
