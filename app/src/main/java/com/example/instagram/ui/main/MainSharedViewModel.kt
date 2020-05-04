package com.example.instagram.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Event
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainSharedViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _homeRedirection: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val homeRedirection: LiveData<Event<Boolean>>
        get() = _homeRedirection
    private val _newPost: MutableLiveData<Event<PostData>> = MutableLiveData()
    val newPost: LiveData<Event<PostData>>
        get() = _newPost

    fun setNewPost(post: PostData) {
        _newPost.value = Event(post)
    }

    fun onHomeRedirect() {
        _homeRedirection.postValue(Event(true))
    }

    override fun onCreate() {
//        TODO("Not yet implemented")
    }
}