package com.mobile.vision.data.api

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mobile.vision.di.components.DaggerEventBusComponent
import com.mobile.vision.di.components.EventBusComponent
import com.mobile.vision.di.modules.EventBusModule
import com.mobile.vision.di.qualifier.FirebaseFirestoreDbInfo
import com.mobile.vision.di.qualifier.ImageUploadProgressInfo
import com.mobile.vision.di.qualifier.StorageReferenceInfo
import io.reactivex.Observable
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

    private val eventBusComponent : EventBusComponent by lazy {
       DaggerEventBusComponent.builder()
               .eventBusModule(EventBusModule())
               .build()
    }

    override fun uploadImageFile(filePath: String) {
        val filePathUri = Uri.fromFile(File(filePath))
        storageReference.child("images/${UUID.randomUUID()}")
                .putFile(filePathUri)
                .addOnSuccessListener {
                    info("Image uploaded ${it.downloadUrl}")
                    eventBusComponent.getImageUploadSubject().onNext(true)
                }
                .addOnFailureListener {
                    error("Failed to upload image with error ${it.message}", it)
                    eventBusComponent.getImageUploadSubject().onNext(false)
                }
                .addOnProgressListener {
                    info("Image upload progress, ${it.bytesTransferred / it.totalByteCount}")
                    eventBusComponent.getImageUploadProgressSubject().onNext((it.bytesTransferred / it.totalByteCount).toInt())
                }
    }

    override fun retrieveVisionApiData() {
        firestore.collection("images").document(storageReference.name)
                .addSnapshotListener({ documentSnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        error("Failed to retrieve Vision API with error ${firebaseFirestoreException.message}",
                                firebaseFirestoreException)
                    } else {
                        if (documentSnapshot.exists()) {
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