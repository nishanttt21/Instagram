package com.example.instagram.ui.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.R
import com.example.instagram.data.model.Post
import com.example.instagram.data.repository.PhotoRepository
import com.example.instagram.data.repository.PostRepository
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.Event
import com.example.instagram.utils.common.FileUtils
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.log.Logger
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.InputStream

class PhotoViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val directory: File,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val photoRepository: PhotoRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _loadingProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    val loadingProgressBar: LiveData<Boolean>
        get() = _loadingProgressBar
    private val user = userRepository.getCurrentUser()!!
    private val _post: MutableLiveData<Event<Post>> = MutableLiveData()
    val post: LiveData<Event<Post>>
        get() = _post

    override fun onCreate() {
//        TODO("Not yet implemented")
    }

    fun onGalleryImageSelected(inputStream: InputStream) {
        _loadingProgressBar.postValue(true)
        compositeDisposable.add(
            Single.fromCallable {
                FileUtils.saveInputStreamToFile(inputStream, directory, "gallery_img_temp", 500)
            }.subscribeOn(Schedulers.io()).subscribe({
                if (it != null) {
                    FileUtils.getImageSize(it)?.run {
                        uploadPhotoAndCreatePost(it, this)
                    }
                } else {
                    _loadingProgressBar.postValue(false)
                    messageStringId.postValue(Resource.error(R.string.try_again))
                }
            }, {
                _loadingProgressBar.postValue(false)
                messageStringId.postValue(Resource.error(R.string.try_again))

            })
        )
    }

    fun onCameraImageTaken(cameraImageProcessor: () -> String) {
        _loadingProgressBar.postValue(true)
        compositeDisposable.add(
            Single.fromCallable { cameraImageProcessor() }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    File(it).apply {
                        FileUtils.getImageSize(this)?.let { size ->
                            uploadPhotoAndCreatePost(this, size)
                        } ?: _loadingProgressBar.postValue(false)
                    }
                }, {
                    _loadingProgressBar.postValue(false)
                    messageStringId.postValue(Resource.error(R.string.try_again))
                })
        )
    }

    private fun uploadPhotoAndCreatePost(imageFile: File, imageSize: Pair<Int, Int>) {
        Logger.d("DEBUG", imageFile.path)
        compositeDisposable.add(
            photoRepository.uploadPhoto(imageFile, user)
                .flatMap {
                    postRepository.createPost(it, imageSize.first, imageSize.second, user)
                }
                .subscribeOn(Schedulers.io()).subscribe({
                    _loadingProgressBar.postValue(false)
                    _post.postValue(Event(it))
                }, {
                    handleNetworkError(it)
                    _loadingProgressBar.postValue(false)
                })
        )
    }

}
