package com.example.instagram.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.InstagramApp
import com.example.instagram.R
import com.example.instagram.di.component.DaggerActivityComponent
import com.example.instagram.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerActivityComponent.builder().activityModule(ActivityModule(this))
            .applicationComponent((application as InstagramApp).applicationComponent)
            .build().inject(this)
        textview.text = mainViewModel.getDatabase()
    }
}