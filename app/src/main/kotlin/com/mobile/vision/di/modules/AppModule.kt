package com.mobile.vision.di.modules

import android.app.Application
import android.content.Context
import com.mobile.vision.data.DataManager
import com.mobile.vision.data.DataManagerImpl
import com.mobile.vision.di.qualifier.AppCtxQualifier
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author lusinabrian on 20/09/17.
 * @Notes app module
 */
@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @AppCtxQualifier
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManager: DataManagerImpl): DataManager {
        return dataManager
    }

    @Provides
    @Named("NetworkSubject")
    fun provideNetworkSubject() : PublishSubject<Boolean>{
        return PublishSubject.create()
    }
}