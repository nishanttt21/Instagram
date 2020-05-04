package com.example.instagram.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.model.User
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

class HomeViewModel(
    compositeDisposable: CompositeDisposable,
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val allPostList: ArrayList<PostData>,
    private val paginator: PublishProcessor<Pair<String?, String?>>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _postsData: MutableLiveData<Resource<List<PostData>>> = MutableLiveData()
    val postsData: LiveData<Resource<List<PostData>>>
        get() = _postsData

    private val _refreshPosts: MutableLiveData<Resource<List<PostData>>> = MutableLiveData()
    val refreshPosts: LiveData<Resource<List<PostData>>>
        get() = _refreshPosts

    private val user: User = userRepository.getCurrentUser()!!
    private var firstId: String? = null
    private var lastId: String? = null

    init {
        compositeDisposable.add(
            paginator.onBackpressureDrop()
                .doOnNext {
                    _loading.postValue(true)
                }.concatMapSingle {
                    return@concatMapSingle postRepository.fetchHomePostListCall(
                        it.first,
                        it.second,
                        user
                    )
                        .subscribeOn(Schedulers.io())
                        .doOnError {
                            handleNetworkError(it)
                        }

                }.subscribe({
                    allPostList.addAll(it)
                    firstId = allPostList.maxBy { postData -> postData.createdAt.time }?.id
                    lastId = allPostList.minBy { postData -> postData.createdAt.time }?.id
                    _loading.postValue(false)
                    _postsData.postValue(Resource.success(it))
                }, {
                    handleNetworkError(it)
                })
        )
    }

    override fun onCreate() {
        loadMorePosts()
    }

    private fun loadMorePosts() {
        if (checkInternetConnectionWithMessage()) paginator.onNext(Pair(firstId, lastId))
    }

    fun onLoadMore() {
//        if (_loading.value != null && _loading.value == false ) loadMorePosts()
        _loading.value?.run {
            if (!this) loadMorePosts()
        }
    }

    fun onNewPost(postData: PostData) {
        allPostList.add(0, postData)
        _refreshPosts.postValue(Resource.success(mutableListOf<PostData>().apply {
            addAll(
                allPostList
            )
        }))
    }
}
