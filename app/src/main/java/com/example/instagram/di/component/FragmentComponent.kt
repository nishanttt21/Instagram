package com.example.instagram.di.component

import com.example.instagram.InstagramApp
import com.example.instagram.di.ActivityScope
import com.example.instagram.di.FragmentScope
import com.example.instagram.di.module.ActivityModule
import com.example.instagram.di.module.ApplicationModule
import com.example.instagram.di.module.FragmentModule
import com.example.instagram.ui.HomeFragment
import com.example.instagram.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@FragmentScope
@Component(dependencies = [ApplicationComponent::class] ,modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: HomeFragment)
}
