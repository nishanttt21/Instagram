package com.example.instagram.di.component

import com.example.instagram.InstagramApp
import com.example.instagram.db.DataBaseService
import com.example.instagram.db.NetworkService
import com.example.instagram.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(app: InstagramApp)
    fun getNetworkService():NetworkService
    fun getDatabaseService():DataBaseService
}
