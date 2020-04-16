package com.example.instagram.db

import android.content.Context
import com.example.instagram.di.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(private val context: Context,@NetworkInfo private val key:String) {
    fun getData() :String = key
}
