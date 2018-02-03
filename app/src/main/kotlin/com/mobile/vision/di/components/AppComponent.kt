package com.mobile.vision.di.components

import android.app.Application
import android.content.Context
import com.mobile.vision.di.qualifier.AppCtxQualifier
import com.mobile.vision.app.MobileVisionApp
import com.mobile.vision.data.DataManager
import com.mobile.vision.di.modules.ApiModule
import com.mobile.vision.di.modules.AppModule
import com.mobile.vision.di.modules.EventBusModule
import com.mobile.vision.di.modules.FirebaseModule
import dagger.Component
import io.reactivex.subjects.PublishSubject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author lusinabrian on 20/09/17.
 * @Notes app component
 */
@Singleton
@Component(modules = [(AppModule::class), (FirebaseModule::class), ApiModule::class, EventBusModule::class])
interface AppComponent {
    fun injectApp(mobileVisionApp: MobileVisionApp)

    @AppCtxQualifier
    fun context(): Context

    fun application(): Application

    fun getDataManager(): DataManager

    /**
     * Network Subject. This is posted based on network events
     * */
    @Named("NetworkSubject")
    fun networkSubject() : PublishSubject<Boolean>
}