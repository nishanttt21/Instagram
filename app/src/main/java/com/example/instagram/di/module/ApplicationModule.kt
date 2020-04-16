package com.example.instagram.di.module

import android.content.Context
import androidx.room.Room
import com.example.instagram.InstagramApp
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.local.MIGRATION_1_2
import com.example.instagram.di.ApplicationContext
import com.example.instagram.di.DatabaseInfo
import com.example.instagram.di.NetworkInfo
import com.example.instagram.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val app:InstagramApp) {
    @ApplicationContext
    @Provides
    fun context():Context = app

    @DatabaseInfo
    @Provides
    fun provideDbName():String = "Custom DB"

    @NetworkInfo
    @Provides
    fun provideApiKey():String = "Custom API"

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    @Provides
    fun provideDbVersion():Int = 1
    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService = Room.databaseBuilder(
        app,
        DatabaseService::class.java,
        "bootcamp-database-project-db")
        .addMigrations(MIGRATION_1_2)
        .build()
    @Provides
    fun provideNetworkHelper():NetworkHelper = NetworkHelper(app)
//    @Singleton
//    @Provides
//    fun provideNetworkService():NetworkService = NetworkService(app,"MyKey")
//    @Singleton
//    @Provides
//    fun provideDatabaseService():DataBaseService = DataBaseService(app,"DB name",2)
}