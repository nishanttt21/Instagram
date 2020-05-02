package com.example.instagram

import android.app.Application
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.di.component.ApplicationComponent
import com.example.instagram.di.component.DaggerApplicationComponent
import com.example.instagram.di.module.ApplicationModule
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class InstagramApp : Application() {
    companion object{
        private val TAG = InstagramApp::class.simpleName
    }
    lateinit var applicationComponent: ApplicationComponent
    @Inject
    lateinit var networkService: NetworkService
    @Inject
    lateinit var databaseService : DatabaseService
    override fun onCreate() {
        super.onCreate()
        applicationComponent = getDependencies()
        applicationComponent.inject(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
    fun getDependencies()=
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
}