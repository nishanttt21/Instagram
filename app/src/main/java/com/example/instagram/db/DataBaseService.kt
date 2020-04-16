package com.example.instagram.db

import android.content.Context
import com.example.instagram.di.DatabaseInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseService @Inject constructor(private val context: Context, @DatabaseInfo private val db:String, private val version:Int) {
    fun getData():String = "$db : $version"
}
