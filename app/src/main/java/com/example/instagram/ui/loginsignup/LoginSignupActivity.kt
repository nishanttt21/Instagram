package com.example.instagram.ui.loginsignup

import android.os.Bundle
import com.example.instagram.R
import com.example.instagram.databinding.ActivityLoginSignupBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity

/**
 * Created by @author Deepak Dawade on 5/1/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class LoginSignupActivity : BaseActivity<ActivityLoginSignupBinding, LoginSignupViewModel>() {
    override fun provideLayoutId(): Int = R.layout.activity_login_signup

    override fun setupView(savedInstanceState: Bundle?) {
        //:TODO("Not yet implemented")
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)
}