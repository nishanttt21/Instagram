package com.example.instagram.di.module

import android.content.Context
import com.example.instagram.InstagramApp
import com.example.instagram.db.DataBaseService
import com.example.instagram.db.NetworkService
import com.example.instagram.di.DatabaseInfo
import com.example.instagram.di.NetworkInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app:InstagramApp) {
    @Provides
    fun context():Context = app

    @DatabaseInfo
    @Provides
    fun provideDbName():String = "Custom DB"

    @NetworkInfo
    @Provides
    fun provideApiKey():String = "Custom API"

    @Provides
    fun provideDbVersion():Int = 1

//    @Singleton
//    @Provides
//    fun provideNetworkService():NetworkService = NetworkService(app,"MyKey")
//    @Singleton
//    @Provides
//    fun provideDatabaseService():DataBaseService = DataBaseService(app,"DB name",2)
}