package com.example.instagram.di.component

import com.example.instagram.InstagramApp
import com.example.instagram.di.ActivityScope
import com.example.instagram.di.module.ActivityModule
import com.example.instagram.di.module.ApplicationModule
import com.example.instagram.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@ActivityScope
@Component(dependencies = [ApplicationComponent::class] ,modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}
