package com.example.instagram.di.component

import com.example.instagram.di.ActivityScope
import com.example.instagram.di.module.ActivityModule
import com.example.instagram.ui.dummy.DummyActivity
import com.example.instagram.ui.loginsignup.LoginSignupActivity
import com.example.instagram.ui.main.MainActivity
import com.example.instagram.ui.post.postdetail.PostDetailActivity
import com.example.instagram.ui.profile.editprofile.EditProfileActivity
import com.example.instagram.ui.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface
ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(activity: SplashActivity)

    fun inject(activity: DummyActivity)
    fun inject(loginSignupActivity: LoginSignupActivity)
    fun inject(editProfileActivity: EditProfileActivity)
    fun inject(postDetailActivity: PostDetailActivity)

}
