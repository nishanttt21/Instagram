package com.example.instagram.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.instagram.R
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.home.postdetail.PostDetailFragment
import java.io.FileNotFoundException
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        private const val TAG = "MainActivity"
        private const val OPEN_GALLERY_REQUEST_CODE = 111
        private const val USER_ID = "userId"
        fun getStartIntent(
            context: Context, userId: String
        ): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(USER_ID, userId)
            }
        }
    }
    @Inject
    lateinit var sharedViewModel: MainSharedViewModel
    override fun provideLayoutId(): Int = R.layout.activity_main
    private lateinit var navController: NavController
    override fun setupView(savedInstanceState: Bundle?) {
        navController = this.findNavController(R.id.mainNavHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        val menu =  binding.bottomNavigation.menu
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            menu.findItem(R.id.profileFragment).iconTintList = null
            menu.findItem(R.id.profileFragment).iconTintMode = null
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.userProfile.observe(this, Observer {
            it?.let {
                if (it.isEmpty())
                    loadIcon(it)
                else openGalley()
            }?:openGalley()
        })
    }
    private fun openGalley(){
        Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }.also {
            startActivityForResult(it, OPEN_GALLERY_REQUEST_CODE)
        }
    }

    private fun loadIcon(url:String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val drawable: Drawable = BitmapDrawable(resources, resource)
                    binding.bottomNavigation.menu.findItem(R.id.profileFragment).icon = drawable
                }
            })
    }
    override fun injectDependencies(activityComponent: ActivityComponent):
            Unit = activityComponent.inject(this)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_GALLERY_REQUEST_CODE -> {
                    try {
                        intent?.data?.let {
                            loadIcon(it.toString())
                        } ?: showMessage(R.string.try_again)
                    } catch (e: FileNotFoundException) {
                        showMessage(R.string.try_again)
                    }
                }
            }
        }
    }
}