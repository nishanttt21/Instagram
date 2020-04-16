package com.example.instagram.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instagram.InstagramApp
import com.example.instagram.R
import com.example.instagram.di.component.DaggerActivityComponent
import com.example.instagram.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        getDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addHomeFragment()

        viewModel.user.observe(this, Observer {
            textview.text = it.toString()
        })

        viewModel.allUser.observe(this, Observer {
            textview.text = it.toString()
        })
    }

    private fun addHomeFragment() {
        if (supportFragmentManager.findFragmentByTag(HomeFragment.TAG) == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit()
        }
    }

    private fun getDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as InstagramApp).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
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
}