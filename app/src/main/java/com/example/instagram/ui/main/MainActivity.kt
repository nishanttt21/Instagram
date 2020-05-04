package com.example.instagram.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.instagram.R
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    @Inject
    lateinit var sharedViewModel: MainSharedViewModel
    override fun provideLayoutId(): Int = R.layout.activity_main
    private lateinit var navController: NavController
    override fun setupView(savedInstanceState: Bundle?) {
        navController = this.findNavController(R.id.mainNavHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun injectDependencies(activityComponent: ActivityComponent):
            Unit = activityComponent.inject(this)
    companion object {
        private const val TAG = "MainActivity"
    }
}