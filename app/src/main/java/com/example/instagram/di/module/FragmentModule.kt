package com.example.instagram.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.di.ActivityContext
import com.example.instagram.ui.HomeViewModel
import com.example.instagram.utils.NetworkHelper
import com.example.instagram.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class FragmentModule(private val fragment: Fragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.requireContext()

    @Provides
    fun provideHomeViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): HomeViewModel = ViewModelProvider(fragment, ViewModelProviderFactory(
        HomeViewModel::class
    ) { HomeViewModel(compositeDisposable, databaseService, networkService, networkHelper) }
    ).get(HomeViewModel::class.java)

}