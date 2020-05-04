package com.example.instagram.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.repository.DummyRepository
import com.example.instagram.data.repository.PhotoRepository
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.di.ActivityContext
import com.example.instagram.di.TempDirectory
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.dummies.DummiesAdapter
import com.example.instagram.ui.dummies.DummiesViewModel
import com.example.instagram.ui.home.HomeViewModel
import com.example.instagram.ui.loginsignup.login.signup.SignUpViewModel
import com.example.instagram.ui.loginsignup.login.signup.login.LoginViewModel
import com.example.instagram.ui.main.MainSharedViewModel
import com.example.instagram.ui.myactivity.MyActivityViewModel
import com.example.instagram.ui.photo.PhotoViewModel
import com.example.instagram.ui.profile.ProfileViewModel
import com.example.instagram.ui.search.SearchViewModel
import com.example.instagram.utils.ViewModelProviderFactory
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import com.mindorks.paracamera.Camera
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import java.io.File


@Module
class FragmentModule(private val fragment: BaseFragment<*, *>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.requireContext()

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
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        repository: UserRepository
    ): LoginViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(schedulerProvider, compositeDisposable, networkHelper, repository)
        }).get(LoginViewModel::class.java)

    @Provides
    fun provideSignUpViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        repository: UserRepository
    ): SignUpViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(SignUpViewModel::class) {
            SignUpViewModel(schedulerProvider, compositeDisposable, networkHelper, repository)
        }).get(SignUpViewModel::class.java)

    @Provides
    fun provideCamera(): Camera = Camera.Builder()
        .resetToCorrectOrientation(true)
        .setTakePhotoRequestCode(1)
        .setDirectory("temp")
        .setName("camera_temp_image")
        .setImageFormat(Camera.IMAGE_JPEG)
        .setCompression(75)
        .setImageHeight(500)
        .build(fragment)

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
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        photoRepository: PhotoRepository, postRepository: PostRepository,
        @TempDirectory directory: File
    ): PhotoViewModel =
        ViewModelProvider(fragment,
            ViewModelProviderFactory(PhotoViewModel::class) {
                PhotoViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper,
                    directory,
                    userRepository,
                    postRepository,
                    photoRepository
                )
            }
        ).get(PhotoViewModel::class.java)

    @Provides
    fun provideProfileViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): ProfileViewModel =
        ViewModelProvider(fragment,
            ViewModelProviderFactory(ProfileViewModel::class) {
                ProfileViewModel(
                    schedulerProvider,
                    compositeDisposable,
                    networkHelper,
                    userRepository
                )
            }
        ).get(ProfileViewModel::class.java)

    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProvider(
        fragment.requireActivity(), ViewModelProviderFactory(
            MainSharedViewModel::class
        ) {
            MainSharedViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }
    ).get(MainSharedViewModel::class.java)

    @Provides
    fun provideMyActivityViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MyActivityViewModel = ViewModelProvider(
        fragment.requireActivity(), ViewModelProviderFactory(
            MyActivityViewModel::class
        ) {
            MyActivityViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }
    ).get(MyActivityViewModel::class.java)

    @Provides
    fun provideSearchViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): SearchViewModel = ViewModelProvider(
        fragment.requireActivity(), ViewModelProviderFactory(
            SearchViewModel::class
        ) {
            SearchViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }
    ).get(SearchViewModel::class.java)

    @Provides
    fun provideDummiesAdapter() = DummiesAdapter(fragment.lifecycle, ArrayList())
}
