package com.example.instagram.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.di.ActivityContext
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.main.MainViewModel
import com.example.instagram.utils.NetworkHelper
import com.example.instagram.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {
    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): MainViewModel = ViewModelProvider(activity, ViewModelProviderFactory(
        MainViewModel::class
    ) {
        MainViewModel(
            compositeDisposable,
            networkHelper,
            databaseService,
            networkService
        )
    }
    ).get(MainViewModel::class.java)
//    @Provides
//    fun provideMainViewModule(networkService: NetworkService,dataBaseService: DataBaseService):
//            MainViewModel= MainViewModel(networkService,dataBaseService)


}