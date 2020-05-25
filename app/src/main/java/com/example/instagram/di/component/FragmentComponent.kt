package com.example.instagram.di.component

import com.example.instagram.di.FragmentScope
import com.example.instagram.di.module.FragmentModule
import com.example.instagram.ui.dummies.DummiesFragment
import com.example.instagram.ui.home.HomeFragment
import com.example.instagram.ui.loginsignup.login.LoginFragment
import com.example.instagram.ui.loginsignup.signup.SignUpFragment
import com.example.instagram.ui.myactivity.MyActivityFragment
import com.example.instagram.ui.photo.PhotoFragment
import com.example.instagram.ui.profile.ProfileFragment
import com.example.instagram.ui.profile.mypost.MyPostFragment
import com.example.instagram.ui.search.SearchFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class] ,modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: PhotoFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: DummiesFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: MyActivityFragment)
    fun inject(fragment: MyPostFragment)
}

