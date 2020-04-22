package com.example.instagram.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.repository.DummyRepository
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.di.ActivityContext
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.dummies.DummiesAdapter
import com.example.instagram.ui.dummies.DummiesViewModel
import com.example.instagram.ui.home.HomeViewModel
import com.example.instagram.ui.home.posts.PostAdapter
import com.example.instagram.ui.photo.PhotoViewModel
import com.example.instagram.ui.profile.ProfileViewModel
import com.example.instagram.utils.ViewModelProviderFactory
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor


@Module
class FragmentModule(private val fragment: BaseFragment<*, *>) {

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
        userRepository: UserRepository,
        postRepository: PostRepository
    ): HomeViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(
            HomeViewModel::class
        ) {
            HomeViewModel(
                compositeDisposable,
                schedulerProvider,
                networkHelper,
                userRepository,
                postRepository,
                ArrayList(),
                PublishProcessor.create()
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
    fun providePhotoViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): PhotoViewModel =
        ViewModelProvider(fragment,
            ViewModelProviderFactory(PhotoViewModel::class) {
                PhotoViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper
                )
            }
        ).get(PhotoViewModel::class.java)

    @Provides
    fun provideProfileViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): ProfileViewModel =
        ViewModelProvider(fragment,
            ViewModelProviderFactory(ProfileViewModel::class) {
                ProfileViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper
                )
            }
        ).get(ProfileViewModel::class.java)

    @Provides
    fun provideDummiesAdapter() = DummiesAdapter(fragment.lifecycle, ArrayList())
}