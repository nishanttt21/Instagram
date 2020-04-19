package com.example.instagram.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.repository.DummyRepository
import com.example.instagram.di.ActivityContext
import com.example.instagram.ui.dummies.DummiesAdapter
import com.example.instagram.ui.dummies.DummiesViewModel
import com.example.instagram.ui.home.HomeViewModel
import com.example.instagram.ui.post.PostAdapter
import com.example.instagram.utils.ViewModelProviderFactory
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class FragmentModule(private val fragment: Fragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.requireContext()

    @Provides
    fun providePostAdapter(): PostAdapter = PostAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideHomeViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): HomeViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(
            HomeViewModel::class
        ) {
            HomeViewModel(
                compositeDisposable,
                schedulerProvider,
                databaseService,
                networkService,
                networkHelper
            )
        }
    ).get(HomeViewModel::class.java)

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun provideDummiesViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        dummyRepository: DummyRepository
    ): DummiesViewModel =
        ViewModelProvider(fragment,
            ViewModelProviderFactory(DummiesViewModel::class) {
                DummiesViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper,
                    dummyRepository
                )
            }
        ).get(DummiesViewModel::class.java)

    @Provides
    fun provideDummiesAdapter() = DummiesAdapter(fragment.lifecycle, ArrayList())
}