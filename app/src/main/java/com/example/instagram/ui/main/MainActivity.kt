package com.example.instagram.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    private fun addHomeFragment() {
        if (supportFragmentManager.findFragmentByTag(HomeFragment.TAG) == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllAddress()
        viewModel.getAllUser()
    }

    override fun onStop() {
        super.onStop()
//        viewModel.deleteUser()
        viewModel.deleteAddress()
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {
        addHomeFragment()
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupObservers() {
        super.setupObservers()
        viewModel.data.observe(this, Observer {
            textview.text = it
        })


        viewModel.user.observe(this, Observer {
            textview.text = it.toString()
        })

        viewModel.allUser.observe(this, Observer {
            textview.text = it.toString()
        })
    }
}