package com.mobile.vision.di.modules

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.chatbot.data.io.SchedulerProvider
import com.chatbot.data.io.SchedulerProviderImpl
import com.mobile.vision.di.qualifier.ActivityCtxQualifier
import com.mobile.vision.di.scopes.ActivityScope
import com.mobile.vision.ui.main.MainPresenter
import com.mobile.vision.ui.main.MainPresenterImpl
import com.mobile.vision.ui.main.MainView
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * @author lusinabrian on 20/09/17.
 * @Notes module for activities
 */
@Module
class ActivityModule(val mActivity: AppCompatActivity) {

    @Provides
    @ActivityCtxQualifier
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    fun provideSchedulers(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @ActivityScope
    fun provideMainPresenter(mainPresenter: MainPresenterImpl<MainView>): MainPresenter<MainView> {
        return mainPresenter
    }
}