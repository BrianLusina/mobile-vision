package com.mobile.vision.di.components

import com.mobile.vision.di.modules.ActivityModule
import com.mobile.vision.di.scopes.ActivityScope
import com.mobile.vision.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [(ActivityModule::class)], dependencies = [(AppComponent::class)])
interface ActivityComponent {

    fun injectMainActivity(mainActivity: MainActivity)

}