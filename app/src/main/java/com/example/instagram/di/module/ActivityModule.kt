package com.example.instagram.di.module

import android.app.Activity
import android.content.Context
import com.example.instagram.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity:Activity) {
    @ActivityContext
    @Provides
    fun context(): Context = activity
//    @Provides
//    fun provideMainViewModule(networkService: NetworkService,dataBaseService: DataBaseService):
//            MainViewModel= MainViewModel(networkService,dataBaseService)


}