package com.example.instagram.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.model.Post
import com.example.instagram.data.model.User
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    compositeDisposable: CompositeDisposable,
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val allPostList: ArrayList<Post>,
    private val paginator: PublishProcessor<Pair<String?, String?>>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>>
        get() = _posts

    private val user: User = userRepository.getCurrentUser()!!

    init {
        compositeDisposable.add(
            paginator.onBackpressureDrop()
                .doOnNext {
                    _loading.postValue(true)
                }.concatMapSingle { postIds ->
                    return@concatMapSingle postRepository.fetchHomePostListCall(
                        postIds.first,
                        postIds.second,
                        user
                    )
                        .subscribeOn(Schedulers.io())
                        .doOnError {
                            handleNetworkError(it)
                        }

                }.subscribe({
                    allPostList.addAll(it)
                    _loading.postValue(false)
                    _posts.postValue(Resource.success(it))
                }, {
                    handleNetworkError(it)
                })
        )
    }

    override fun onCreate() {
        loadMorePosts()
    }

    private fun loadMorePosts() {
        if (allPostList.isNotEmpty())
            allPostList.run {
                if (checkInternetConnectionWithMessage()) paginator.onNext(
                    Pair(
                        first().id,
                        last().id
                    )
                )
            }
        else paginator.onNext(Pair(null, null))
    }

    fun onLoadMore() {
//        if (_loading.value != null && _loading.value == false ) loadMorePosts()
        _loading.value?.run {
            if (!this) loadMorePosts()
        }
    }
}
