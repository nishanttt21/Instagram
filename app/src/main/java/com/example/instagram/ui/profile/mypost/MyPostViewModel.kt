package com.example.instagram.ui.profile.mypost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.instagram.data.model.Post
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 5/21/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class MyPostViewModel @Inject constructor(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        private val postRepository: PostRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _myPostList: MutableLiveData<List<Post>> = MutableLiveData()
    val myPosts: LiveData<List<Post>> =  Transformations.map(_myPostList) { it }
    private val _progress:MutableLiveData<Boolean> = MutableLiveData()
    val progress:LiveData<Boolean> get() = _progress
    private val currentUser = userRepository.getCurrentUser()!!
    override fun onCreate() {
        _progress.value = true
        compositeDisposable.addAll(
                postRepository.fetchMyPostListCall(currentUser)
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            it?.let {
                                _myPostList.postValue(it)
                                _progress.postValue(false)
                            }
                        }, {
                            handleNetworkError(it)
                            _progress.postValue(false)
                        })
        )
    }
    fun fetchMyPostList() {
    }
}