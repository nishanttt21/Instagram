package com.example.instagram.ui.profile.mypost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.instagram.data.model.Image
import com.example.instagram.data.model.Post
import com.example.instagram.data.remote.Networking
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseItemViewModel
import com.example.instagram.utils.display.ScreenUtils
import com.example.instagram.utils.log.Logger
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyPostItemViewModel @Inject constructor(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseItemViewModel<Post>(schedulerProvider, compositeDisposable, networkHelper) {
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
    val post : LiveData<String> = Transformations.map(data){it.imgUrl}
    val imageDetail: LiveData<Image> = Transformations.map(data) {
        it.run {
            Image(
                imgUrl,
                headers,
                screenWidth,
                screenHeight.let { height ->
                    return@let (calculateScaleFactor(this) * height).toInt()
                })

        }
    }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }
    private fun calculateScaleFactor(post: Post) =
        post.imgWidth.let { return@let screenWidth.toFloat() / it }
}
