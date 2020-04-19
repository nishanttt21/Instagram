package com.example.instagram.di.component

import com.example.instagram.di.ActivityScope
import com.example.instagram.di.module.ActivityModule
import com.example.instagram.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class] ,modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}
