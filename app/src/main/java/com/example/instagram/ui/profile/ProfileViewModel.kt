package com.example.instagram.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.model.Me
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val _goToLogin: MutableLiveData<Boolean> = MutableLiveData(false)
    val goToLogin: LiveData<Boolean>
        get() = _goToLogin
    private val _myInfo: MutableLiveData<Me> = MutableLiveData()
    val myInfo: LiveData<Me>
        get() = _myInfo

    override fun onCreate() {
//        fetchMyInfo()
    }

    fun fetchMyInfo() {
        compositeDisposable.addAll(
            userRepository.fetchMyInfo()
                .subscribeOn(Schedulers.io())
                .subscribe({ user ->
                    user?.run {
                        _myInfo.postValue(this)
                    }
                }, {
                    handleNetworkError(it)
                })
        )
    }

    fun onLogoutClick() {
        compositeDisposable.addAll(
            userRepository.doSignOutCall()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        userRepository.removeCurrentUser()
                        _goToLogin.postValue(true)
                    }, {
                        handleNetworkError(it)
                    }
                )
        )

    }

}