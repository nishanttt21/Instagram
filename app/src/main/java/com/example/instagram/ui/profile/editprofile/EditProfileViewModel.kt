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
    private val _myInfo: MutableLiveData<Me> = MutableLiveData()
    val myInfo: LiveData<Me>
        get() = _myInfo

    private val _profilePic: MutableLiveData<String> = MutableLiveData()
    val profilePic: LiveData<String> get() = _profilePic
    override fun onCreate() {

    }

    fun currentUser() = userRepository.getCurrentUser()
    fun fetchMyInfo() {
        compositeDisposable.addAll(
            userRepository.fetchMyInfo()
                .subscribeOn(Schedulers.io())
                .subscribe({ me ->
                    me?.run {
                        _myInfo.postValue(this)
                        profilePicUrl?.let {
                            _profilePic.postValue(it)
                        }
                    }
                }, {
                    handleNetworkError(it)
                })
        )
    }

    fun updateData(user: User) {
        _status.postValue(true)
        compositeDisposable.addAll(
            userRepository.updateMyInfo(user)
                .subscribeOn(Schedulers.io())
                .subscribe({
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
            }.subscribeOn(Schedulers.io())
                .subscribe({
                    _profilePic.postValue(it?.path)
                }, {
                    messageStringId.postValue(Resource.error(R.string.try_again))

                })
        )
    }
}