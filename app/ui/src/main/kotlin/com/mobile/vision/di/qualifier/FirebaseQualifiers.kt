package com.mobile.vision.di.qualifier

import javax.inject.Qualifier

/**
 * @author lusinabrian on 03/02/18.
 * @Notes Qualifier for Firebase Module
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirebaseStorageInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirebaseFirestoreDbInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StorageReferenceInfo

