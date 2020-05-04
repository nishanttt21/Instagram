package com.example.instagram.ui.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.R
import com.example.instagram.data.model.Me
import com.example.instagram.data.model.User
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.common.FileUtils
import com.example.instagram.utils.common.Resource
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.InputStream

class EditProfileViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val directory: File
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _status: MutableLiveData<Boolean> = MutableLiveData()
    val status: LiveData<Boolean>
        get() = _status
    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String> get() = _username
    private val _userEmail: MutableLiveData<String> = MutableLiveData()
    val userEmail: LiveData<String> get() = _userEmail
    private val _userBio: MutableLiveData<String> = MutableLiveData()
    val userBio: LiveData<String> get() = _userBio
    private val _userProfilePic: MutableLiveData<String> = MutableLiveData()
    val userProfilePic: LiveData<String> get() = _userProfilePic

    override fun onCreate() {
        _userEmail.value = userRepository.getCurrentUser()?.email
    }

    fun currentUser() = userRepository.getCurrentUser()
    fun fetchMyInfo() {
        compositeDisposable.addAll(
            userRepository.fetchMyInfo()
                .subscribeOn(Schedulers.io())
                .subscribe({ me ->
                    me?.run {
                        _username.postValue(name)
                        _userBio.postValue(tagline)
                        _userProfilePic.postValue(profilePicUrl)
                    }
                }, {
                    handleNetworkError(it)
                })
        )
    }

    fun updateData(user: User) {
        val me = Me(
            id = user.id,
            name = user.name,
            tagline = user.tagline,
            profilePicUrl = user.profilePicUrl
        )
        _status.postValue(true)
        compositeDisposable.addAll(
            userRepository.updateMyInfo(me)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    userRepository.removeCurrentUser()
                    userRepository.saveCurrentUser(user)
                    _status.postValue(false)
                }, {
                    handleNetworkError(it)
                    _status.postValue(false)
                })
        )
    }

    fun onGalleryImageSelected(inputStream: InputStream) {
        compositeDisposable.add(
            Single.fromCallable {
                FileUtils.saveInputStreamToFile(inputStream, directory, "gallery_img_temp", 500)
            }.subscribeOn(Schedulers.io()).subscribe({
                if (it != null) {
                    FileUtils.getImageSize(it)?.run {
                        _userProfilePic.postValue(it.path)
                    }
                } else {
                    messageStringId.postValue(Resource.error(R.string.try_again))
                }
            }, {
                messageStringId.postValue(Resource.error(R.string.try_again))

            })
        )
    }
}