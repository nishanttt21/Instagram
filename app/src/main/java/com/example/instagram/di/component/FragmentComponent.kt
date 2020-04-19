package com.example.instagram.di.component

import com.example.instagram.di.FragmentScope
import com.example.instagram.di.module.FragmentModule
import com.example.instagram.ui.dummies.DummiesFragment
import com.example.instagram.ui.home.HomeFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class] ,modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DummiesFragment)
}
