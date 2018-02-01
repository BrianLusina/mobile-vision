package com.mobile.vision.di.modules

import android.app.Application
import android.content.Context
import com.mobile.vision.data.DataManager
import com.mobile.vision.data.DataManagerImpl
import com.mobile.vision.data.files.FileHelper
import com.mobile.vision.data.files.FileHelperImpl
import com.mobile.vision.data.prefs.SharedPrefsHelper
import com.mobile.vision.data.prefs.SharedPrefsHelperImpl
import com.mobile.vision.di.qualifier.AppCtxQualifier
import com.mobile.vision.di.qualifier.PreferenceInfo
import com.mobile.vision.utils.SHARED_PREFS_NAME
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
    @PreferenceInfo
    fun provideSharedPrefsName(): String{
        return SHARED_PREFS_NAME
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManager: DataManagerImpl): DataManager {
        return dataManager
    }

    @Provides
    @Singleton
    fun provideFileHelper(fileHelper: FileHelperImpl): FileHelper {
        return fileHelper
    }

    @Provides
    @Singleton
    fun providePrefsHelper(sharedPrefsHelper: SharedPrefsHelperImpl): SharedPrefsHelper {
        return sharedPrefsHelper
    }

    @Provides
    @Named("NetworkSubject")
    fun provideNetworkSubject() : PublishSubject<Boolean>{
        return PublishSubject.create()
    }
}