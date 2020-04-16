package com.example.instagram.di.module

import android.app.Activity
import android.content.Context
import com.example.instagram.db.DataBaseService
import com.example.instagram.db.NetworkService
import com.example.instagram.ui.MainActivity
import com.example.instagram.ui.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity:Activity) {
    @Provides
    fun context(): Context = activity
//    @Provides
//    fun provideMainViewModule(networkService: NetworkService,dataBaseService: DataBaseService):
//            MainViewModel= MainViewModel(networkService,dataBaseService)


}