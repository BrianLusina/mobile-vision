package com.mobile.vision.data.api

import android.net.Uri
import com.crashlytics.android.Crashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mobile.vision.data.events.ImageUploadEvent
import com.mobile.vision.di.qualifier.FirebaseFirestoreDbInfo
import com.mobile.vision.di.qualifier.StorageReferenceInfo
import com.rollbar.notifier.Rollbar
import io.fabric.sdk.android.Fabric
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 17/01/18.
 * @Notes Api Helper implementation
 */
@Singleton
class ApiHelperImpl @Inject constructor(@StorageReferenceInfo val storageReference: StorageReference,
                                        @FirebaseFirestoreDbInfo val firestore: FirebaseFirestore) : ApiHelper, AnkoLogger {

    override fun uploadImageFile(filePath: String) {
        val filePathUri = Uri.fromFile(File(filePath))
        storageReference.child("images/${UUID.randomUUID()}")
                .putFile(filePathUri)
                .addOnSuccessListener {
                    Fabric.getLogger().i("ImageUpload", "Image Uploaded, download url ${it.downloadUrl}")
                    EventBus.getDefault().post(ImageUploadEvent(true))
                    retrieveVisionApiData()
                }
                .addOnFailureListener {
                    error("Failed to upload image with error ${it.message}", it)
                    EventBus.getDefault().post(ImageUploadEvent(false, isUploading = false))
                }
                .addOnProgressListener {
                    info("Image upload progress, ${it.bytesTransferred / it.totalByteCount}")
                    EventBus.getDefault().post(ImageUploadEvent(uploadProgress = (
                            it.bytesTransferred / it.totalByteCount).toInt(),
                            isUploading = true)
                    )
                }
    }

    override fun retrieveVisionApiData() {
        //todo: java.lang.IllegalArgumentException: Invalid document reference. Document references must have an even number of segments, but images has 1
        firestore.collection("images").document(storageReference.name)
                .addSnapshotListener({ documentSnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        error("Failed to retrieve Vision API with error ${firebaseFirestoreException.message}",
                                firebaseFirestoreException)
                    } else {
                        if (documentSnapshot.exists()) {
                            documentSnapshot
                            // parse the response
                            info("Document Snapshot ${documentSnapshot.data}")
                        } else {
                            info("Waiting for Vision API")
                        }
                    }
                })
    }

    override fun parseVisionApiResponse() {

    }
}