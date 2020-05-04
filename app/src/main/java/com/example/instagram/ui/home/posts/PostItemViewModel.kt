package com.example.instagram.ui.home.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.instagram.R
import com.example.instagram.data.model.Image
import com.example.instagram.data.remote.Networking
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseItemViewModel
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.common.TimeUtils
import com.example.instagram.utils.display.ScreenUtils
import com.example.instagram.utils.log.Logger
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class PostItemViewModel @Inject constructor(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseItemViewModel<PostData>(schedulerProvider, compositeDisposable, networkHelper) {
    companion object {
        const val TAG = "PostItemViewModel"
    }

    private val user = userRepository.getCurrentUser()!!
    private val screenWidth = ScreenUtils.getScreenWidth()
    private val screenHeight = ScreenUtils.getScreenHeight()
    private val headers = mapOf(
        Pair(Networking.HEADER_API_KEY, Networking.API_KEY),
        Pair(Networking.HEADER_USER_ID, user.id),
        Pair(Networking.HEADER_ACCESS_TOKEN, user.accessToken)
    )

    val name: LiveData<String> = Transformations.map(data) { it.creator.name }
    val postTime: LiveData<String> =
        Transformations.map(data) { TimeUtils.getTimeAgo(it.createdAt) }
    val likesCount: LiveData<Int> = Transformations.map(data) { it.likedBy?.size ?: 0 }
    val isLiked: LiveData<Boolean> = Transformations.map(data) {
        it.likedBy?.find { postUser -> postUser.id == user.id } != null
    }
    val postId: LiveData<String> = Transformations.map(data) {
        it.id
    }
    val profileImage: LiveData<Image> = Transformations.map(data) {
        it.creator.profilePicUrl?.run { Image(this, headers) }
    }

    val imageDetail: LiveData<Image> = Transformations.map(data) {
        it.run {
            Image(
                imgUrl,
                headers,
                screenWidth,
                imgHeight.let { height ->
                    return@let (calculateScaleFactor(this) * height).toInt()
                })

        }
    }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }

    private fun calculateScaleFactor(post: PostData) =
        post.imgWidth.let { return@let screenWidth.toFloat() / it }

    fun onLikeClick() = data.value?.let {
        if (networkHelper.isNetworkConnected()) {
            val api =
                if (isLiked.value == true)
                    postRepository.makeUnlikePost(it, user)
                else
                    postRepository.makeLikePost(it, user)

            compositeDisposable.add(
                api
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    { responsePost -> if (responsePost.id == it.id) updateData(responsePost) },
                    { error -> Timber.e(error) }
                )
            )
        } else {
            messageStringId.postValue(Resource.error(R.string.network_connection_error))
        }
    }

}