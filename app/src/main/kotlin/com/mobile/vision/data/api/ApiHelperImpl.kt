package com.mobile.vision.data.api

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mobile.vision.di.qualifier.FirebaseFirestoreDbInfo
import com.mobile.vision.di.qualifier.StorageReferenceInfo
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
        var downloadUrl : Uri?
        storageReference.child("images/${UUID.randomUUID()}")
                .putFile(filePathUri)
                .addOnSuccessListener {
                    info("Image uploaded ${it.downloadUrl}")
                    downloadUrl = it.downloadUrl
                }
                .addOnFailureListener {
                    error("Failed to upload image with error ${it.message}", it)
                }
                .addOnProgressListener {
                    info("Image upload progress, ${it.bytesTransferred / it.totalByteCount}")
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