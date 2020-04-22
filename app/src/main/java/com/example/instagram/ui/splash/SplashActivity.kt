package com.example.instagram.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.databinding.ActivitySplashBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.login.LoginActivity
import com.example.instagram.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_splash

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        binding.instagramLogo.setOnClickListener {
            showMessage("Image")
        }
    }

    override fun setupObservers() {
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchMain.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        })
        viewModel.launchLogin.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        })
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}