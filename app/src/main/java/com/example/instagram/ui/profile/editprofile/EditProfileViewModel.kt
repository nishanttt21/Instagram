package com.example.instagram.ui.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.model.User
import com.example.instagram.data.repository.UserRepository
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditProfileViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val repository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    private val _status: MutableLiveData<Boolean> = MutableLiveData()
    val status: LiveData<Boolean>
        get() = _status
    val currentUser: LiveData<User>
        get() = _currentUser

    override fun onCreate() {
        _currentUser.value = repository.getCurrentUser()
    }

    fun updateData(user: User) {
        _status.postValue(true)
        compositeDisposable.addAll(
            repository.updateMyInfo(user)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    repository.saveCurrentUser(user)
                    _status.postValue(false)
                }, {
                    handleNetworkError(it)
                    _status.postValue(false)
                })
        )
    }
}