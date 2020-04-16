package com.example.instagram.data.remote

import android.content.Context
import com.example.instagram.di.ActivityContext
import com.example.instagram.di.ApplicationContext
import com.example.instagram.di.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(@ApplicationContext private val context: Context, @NetworkInfo private val key:String) {
    fun getData() :String = key
}
