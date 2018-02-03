package com.mobile.vision.di.components

import com.mobile.vision.di.modules.EventBusModule
import com.mobile.vision.di.qualifier.ImageUploadListenerInfo
import com.mobile.vision.di.qualifier.ImageUploadProgressInfo
import dagger.Component
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton


/**
 * @author lusinabrian on 03/02/18.
 * @Notes Connector for the Event bus Module
 */
@Singleton
@Component(modules = [EventBusModule::class])
interface EventBusComponent {

    @ImageUploadListenerInfo
    fun getImageUploadSubject(): PublishSubject<Boolean>

    @ImageUploadProgressInfo
    fun getImageUploadProgressSubject(): PublishSubject<Int>
}