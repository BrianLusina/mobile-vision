package com.mobile.vision.di.modules

import com.mobile.vision.di.qualifier.ImageUploadListenerInfo
import com.mobile.vision.di.qualifier.ImageUploadProgressInfo
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton


/**
 * @author lusinabrian on 03/02/18.
 * @Notes Custom Event Bus Module that allows communication with components in the application
 */
@Module
class EventBusModule {

    @Provides
    @Singleton
    @ImageUploadListenerInfo
    fun provideImageUploadSubject(): PublishSubject<Boolean> {
        return PublishSubject.create()
    }

    @Provides
    @Singleton
    @ImageUploadProgressInfo
    fun provideImageUploadProgressSubject(): PublishSubject<Int> {
        return PublishSubject.create()
    }

}