package com.example.instagram.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.model.Post
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
    private val _newPost: MutableLiveData<Event<Post>> = MutableLiveData()
    val newPost: LiveData<Event<Post>>
        get() = _newPost

    fun setNewPost(post: Post) {
        _newPost.value = Event(post)
    }

    fun onHomeRedirect() {
        _homeRedirection.postValue(Event(true))
    }

    override fun onCreate() {
//        TODO("Not yet implemented")
    }
}