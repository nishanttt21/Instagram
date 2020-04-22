package com.example.instagram.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Event
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _homeNavigation: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val homeNavigation: LiveData<Event<Boolean>>
        get() = _homeNavigation
    private val _photoNavigation: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val photoNavigation: LiveData<Event<Boolean>>
        get() = _photoNavigation
    private val _profileNavigation: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val profileNavigation: LiveData<Event<Boolean>>
        get() = _profileNavigation

    override fun onCreate() {
        _homeNavigation.postValue(Event(true))
    }

    fun onHomeSelected() {
        _homeNavigation.postValue(Event(true))
    }

    fun onProfileSelected() {
        _profileNavigation.postValue(Event(true))
    }

    fun onPhotoSelected() {
        _photoNavigation.postValue(Event(true))
    }
}