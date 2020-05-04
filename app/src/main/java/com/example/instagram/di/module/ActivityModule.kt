package com.example.instagram.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.data.repository.DummyRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.di.ActivityContext
import com.example.instagram.di.TempDirectory
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.dummy.DummyViewModel
import com.example.instagram.ui.loginsignup.LoginSignupViewModel
import com.example.instagram.ui.main.MainSharedViewModel
import com.example.instagram.ui.main.MainViewModel
import com.example.instagram.ui.post.postdetail.PostDetailViewModel
import com.example.instagram.ui.profile.editprofile.EditProfileViewModel
import com.example.instagram.ui.splash.SplashViewModel
import com.example.instagram.utils.ViewModelProviderFactory
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.io.File

@Module
class ActivityModule(private val activity: BaseActivity<*, *>) {
    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(
            MainViewModel::class
        ) {
            MainViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }
    ).get(MainViewModel::class.java)

    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(
            MainSharedViewModel::class
        ) {
            MainSharedViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }
    ).get(MainSharedViewModel::class.java)
//    @Provides
//    fun provideMainViewModule(networkService: NetworkService,dataBaseService: DataBaseService):
//            MainViewModel= MainViewModel(networkService,dataBaseService)

    @Provides
    fun provideSplashViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): SplashViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(SplashViewModel::class) {
            SplashViewModel(schedulerProvider, compositeDisposable, networkHelper, userRepository)
            //this lambda creates and return SplashViewModel
        }).get(SplashViewModel::class.java)

    @Provides
    fun provideDummyViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        dummyRepository: DummyRepository
    ): DummyViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(DummyViewModel::class) {
            DummyViewModel(schedulerProvider, compositeDisposable, networkHelper, dummyRepository)
        }).get(DummyViewModel::class.java)

    @Provides
    fun provideLoginSignupViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): LoginSignupViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(LoginSignupViewModel::class) {
            LoginSignupViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(LoginSignupViewModel::class.java)


    @Provides
    fun provideEditProfileViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        repository: UserRepository,
        @TempDirectory directory: File
    ): EditProfileViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(EditProfileViewModel::class) {
            EditProfileViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                repository,
                directory
            )
        }).get(EditProfileViewModel::class.java)

    @Provides
    fun providePostDetailViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): PostDetailViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(PostDetailViewModel::class) {
            PostDetailViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }).get(PostDetailViewModel::class.java)
}
