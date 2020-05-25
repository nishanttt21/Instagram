package com.example.instagram.ui.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.instagram.R
import com.example.instagram.data.model.Image
import com.example.instagram.data.remote.Networking
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.common.TimeUtils
import com.example.instagram.utils.display.ScreenUtils
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class PostDetailViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val currentUser = userRepository.getCurrentUser()!!
    private val postData: MutableLiveData<PostData> = MutableLiveData()
    val name: LiveData<String> = Transformations.map(postData) { it.creator.name }
    val postTime: LiveData<String> =
        Transformations.map(postData) { TimeUtils.getTimeAgo(it.createdAt) }
    val likesCount: LiveData<Int> = Transformations.map(postData) { it.likedBy?.size ?: 0 }
    val profilePicUrl: LiveData<String> = Transformations.map(postData) { it.creator.profilePicUrl }
    val isLiked: LiveData<Boolean> = Transformations.map(postData) {
        it.likedBy?.find { postUser -> postUser.id == currentUser.id } != null
    }
    private val headers = mapOf(
        Pair(Networking.HEADER_API_KEY, Networking.API_KEY),
        Pair(Networking.HEADER_USER_ID, currentUser.id),
        Pair(Networking.HEADER_ACCESS_TOKEN, currentUser.accessToken)
    )
    val profileImage: LiveData<Image> = Transformations.map(postData) {
        it.creator.profilePicUrl?.run { Image(this, headers) }
    }
    val imageDetail: LiveData<Image> = Transformations.map(postData) {
        it?.run {
            Image(
                imgUrl,
                headers,
                ScreenUtils.getScreenWidth(),
                imgHeight.let { height ->
                    return@let (calculateScaleFactor(this) * height).toInt()
                })

        }
    }
    private val _status :MutableLiveData<Boolean> = MutableLiveData()
    val status: LiveData<Boolean> get() = _status
    override fun onCreate() {
        //:TODO("Not yet implemented")
    }

    private fun calculateScaleFactor(post: PostData) =
        post.imgWidth.let { return@let ScreenUtils.getScreenWidth().toFloat() / it }

    fun getPostDetail(postId: String) {
        compositeDisposable.addAll(
            postRepository.getPostDetail(postId, currentUser).subscribeOn(Schedulers.io())
                .subscribe({
                    postData.postValue(it)
                }, {
                    handleNetworkError(it)
                })
        )
    }

    fun onLikeClick() = postData.value?.let {
        if (networkHelper.isNetworkConnected()) {
            val api =
                if (isLiked.value == true)
                    postRepository.makeUnlikePost(it, currentUser)
                else
                    postRepository.makeLikePost(it, currentUser)

            compositeDisposable.add(
                api.subscribeOn(schedulerProvider.io())
                    .subscribe(
                        { responsePost ->
                            if (responsePost.id == it.id) postData.postValue(
                                responsePost
                            )
                        },
                        { error -> Timber.e(error) }
                    )
            )
        } else {
            messageStringId.postValue(Resource.error(R.string.network_connection_error))
        }
    }

    fun deletePost(postId: String) {
        compositeDisposable.addAll(postRepository.deleteMyPost(postId,currentUser).subscribeOn(Schedulers.io()).subscribe({
            if (it.status == 200 || it.statusCode.equals("success",true)){
                _status.postValue(true)
            }
        },{
            handleNetworkError(it)
        }))
    }

}