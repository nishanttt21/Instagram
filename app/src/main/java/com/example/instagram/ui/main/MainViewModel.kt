package com.example.instagram.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.local.prefs.UserPreferences
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val user = userRepository.getCurrentUser()
    private val _userProfile:MutableLiveData<String?> = MutableLiveData(user?.profilePicUrl)
    val userProfile:LiveData<String?> get()  = _userProfile
    override fun onCreate() {
        _userProfile.value = user?.profilePicUrl
    }
}