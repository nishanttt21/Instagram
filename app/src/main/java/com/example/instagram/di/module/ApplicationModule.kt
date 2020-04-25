package com.example.instagram.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.instagram.BuildConfig
import com.example.instagram.InstagramApp
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.Networking
import com.example.instagram.data.repository.*
import com.example.instagram.di.ApplicationContext
import com.example.instagram.di.DatabaseInfo
import com.example.instagram.di.NetworkInfo
import com.example.instagram.di.TempDirectory
import com.example.instagram.utils.common.FileUtils
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.RxSchedulerProvider
import com.example.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val app:InstagramApp) {
    @ApplicationContext
    @Provides
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @DatabaseInfo
    @Provides
    fun provideDbName(): String = "Custom DB"

    @NetworkInfo
    @Provides
    fun provideApiKey(): String = "Custom API"

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    @TempDirectory
    fun provideTempDirectory() = FileUtils.getDirectory(app, "temp")

    @Provides
    fun provideDbVersion(): Int = 1
//    @Provides
//    @Singleton
//    fun provideDatabaseService(): DatabaseService = Room.databaseBuilder(
//        app,
//        DatabaseService::class.java,
//        "bootcamp-database-project-db")
//        .addMigrations(MIGRATION_1_2)
//        .build()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        app.getSharedPreferences("bootcamp-instagram-project-prefs", Context.MODE_PRIVATE)

    /**
     * We need to write @Singleton on the provide method if we are create the instance inside this method
     * to make it singleton. Even if we have written @Singleton on the instance's class
     */
    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(
            app, DatabaseService::class.java,
            "bootcamp-instagram-project-db"
        ).build()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.API_KEY,
            BuildConfig.BASE_URL,
            app.cacheDir,
            10 * 1024 * 1024 // 10MB
        )

    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    fun providePostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository =
        postRepositoryImpl

    @Provides
    fun providePhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository =
        photoRepositoryImpl

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(app)

//    @Singleton
//    @Provides
//    fun provideNetworkService(): NetworkService = NetworkService(app,"MyKey")
//    @Singleton
//    @Provides
//    fun provideDatabaseService():DataBaseService = DataBaseService(app,"DB name",2)
}