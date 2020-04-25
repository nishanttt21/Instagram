package com.example.instagram.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.instagram.InstagramApp
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.repository.*
import com.example.instagram.di.ApplicationContext
import com.example.instagram.di.TempDirectory
import com.example.instagram.di.module.ApplicationModule
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: InstagramApp)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getCompositeDisposable(): CompositeDisposable

    fun getNetworkHelper(): NetworkHelper

    /**
     * These methods are written in ApplicationComponent because the instance of
     * NetworkService is singleton and is maintained in the ApplicationComponent's implementation by Dagger
     * For NetworkService singleton instance to be accessible to say DummyActivity's DummyViewModel
     * this ApplicationComponent must expose a method that returns NetworkService instance
     * This method will be called when NetworkService is injected in DummyViewModel.
     * Also, in ActivityComponent you can find dependencies = [ApplicationComponent::class] to link this relationship
     */
    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getSharedPreferences(): SharedPreferences

    /**---------------------------------------------------------------------------
     * Dagger will internally create UserRepository instance using constructor injection.
     * Dependency through constructor
     * UserRepository ->
     *  [NetworkService -> Nothing is required],
     *  [DatabaseService -> Nothing is required],
     *  [UserPreferences -> [SharedPreferences -> provided by the function provideSharedPreferences in ApplicationModule class]]
     * So, Dagger will be able to create an instance of UserRepository by its own using constructor injection
     *---------------------------------------------------------------------------------
     */
    fun getUserRepository(): UserRepository
    fun getPostRepository(): PostRepository
    fun getPhotoRepository(): PhotoRepository

    fun getUserRepositoryImpl(): UserRepositoryImpl
    fun getPostRepositoryImpl(): PostRepositoryImpl
    fun getPhotoRepositoryImpl(): PhotoRepositoryImpl

    @TempDirectory
    fun getTempDirectory(): File
    fun getSchedulerProvider(): SchedulerProvider
}