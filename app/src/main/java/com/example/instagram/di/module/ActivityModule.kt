package com.example.instagram.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.repository.DummyRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.di.ActivityContext
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.dummy.DummyViewModel
import com.example.instagram.ui.login.LoginViewModel
import com.example.instagram.ui.main.MainViewModel
import com.example.instagram.ui.signup.SignUpViewModel
import com.example.instagram.ui.splash.SplashViewModel
import com.example.instagram.utils.ViewModelProviderFactory
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
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
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): MainViewModel = ViewModelProvider(activity, ViewModelProviderFactory(
        MainViewModel::class
    ) {
        MainViewModel(
            schedulerProvider,
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
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        repository: UserRepository
    ): LoginViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(schedulerProvider, compositeDisposable, networkHelper, repository)
        }).get(LoginViewModel::class.java)

    @Provides
    fun provideSignUpViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        repository: UserRepository
    ): SignUpViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(SignUpViewModel::class) {
            SignUpViewModel(schedulerProvider, compositeDisposable, networkHelper, repository)
        }).get(SignUpViewModel::class.java)
}
