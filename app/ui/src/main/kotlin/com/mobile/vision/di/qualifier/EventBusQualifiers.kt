package com.mobile.vision.di.qualifier

import javax.inject.Qualifier

/**
 * @author lusinabrian on 03/02/18.
 * @Notes Event Bus qualifiers
 */

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ImageUploadListenerInfo

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ImageUploadProgressInfo
