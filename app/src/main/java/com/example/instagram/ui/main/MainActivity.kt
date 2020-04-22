package com.example.instagram.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.home.HomeFragment
import com.example.instagram.ui.photo.PhotoFragment
import com.example.instagram.ui.profile.ProfileFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private var activeFragment: Fragment? = null

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {

        binding.bottomNavigationView.run {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemHome -> {
                        viewModel.onHomeSelected()
                        true
                    }
                    R.id.itemAddPhoto -> {
                        viewModel.onPhotoSelected()
                        true
                    }
                    R.id.itemProfile -> {
                        viewModel.onProfileSelected()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent):
            Unit = activityComponent.inject(this)

    override fun setupObservers() {
        super.setupObservers()
        viewModel.homeNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showHome() }
        })
        viewModel.photoNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showAddPhoto() }
        })
        viewModel.profileNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showProfile() }
        })
    }

    private fun showAddPhoto() {
        if (activeFragment is PhotoFragment) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(PhotoFragment.TAG)
        if (fragment == null) {
            fragment = PhotoFragment.newInstance()
            fragmentTransaction.add(binding.containerFragment.id, fragment, PhotoFragment.TAG)
        } else fragmentTransaction.show(fragment)
        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment as Fragment)
        fragmentTransaction.commit()
        activeFragment = fragment
    }

    private fun showProfile() {
        if (activeFragment is ProfileFragment) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)
        if (fragment == null) {
            fragment = ProfileFragment.newInstance()
            fragmentTransaction.add(binding.containerFragment.id, fragment, ProfileFragment.TAG)
        } else fragmentTransaction.show(fragment)
        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment as Fragment)
        fragmentTransaction.commit()
        activeFragment = fragment
    }

    private fun showHome() {
        if (activeFragment is HomeFragment) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG)
        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            fragmentTransaction.add(binding.containerFragment.id, fragment, HomeFragment.TAG)
        } else fragmentTransaction.show(fragment)
        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment as Fragment)
        fragmentTransaction.commit()
        activeFragment = fragment

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}