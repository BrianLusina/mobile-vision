package com.mobile.vision.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mobile.vision.di.qualifier.*

/**
 * @author lusinabrian on 08/11/17.
 * @Notes Firebase module to provide dependencies for Firebase
 */
@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    @FirebaseStorageInfo
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    @StorageReferenceInfo
    fun provideStorageReference(@PreferenceInfo firebaseStorage : FirebaseStorage) : StorageReference{
       return firebaseStorage.reference
    }

    @Provides
    @Singleton
    @FirebaseFirestoreDbInfo
    fun provideFirebaseFirestoreDb() = FirebaseFirestore.getInstance()

}