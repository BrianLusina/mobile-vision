package com.mobile.vision.di.modules

import com.mobile.vision.data.api.ApiHelper
import com.mobile.vision.data.api.ApiHelperImpl
import dagger.Module
import dagger.Provides

/**
 * @author lusinabrian on 17/01/18.
 * @Notes
 */
@Module
class ApiModule {

    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl) : ApiHelper {
        return apiHelper
    }
}