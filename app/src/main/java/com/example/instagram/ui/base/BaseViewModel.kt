package com.example.instagram.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper
) : ViewModel() {
    val messageStringId = MutableLiveData<Int>()
    val messageString = MutableLiveData<String>()

    protected fun checkInternetConnection(): Boolean = networkHelper.isConnected()
    protected fun handleNetworkError(t: Throwable) {
        TODO("Later")
    }

    abstract fun onCreate()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}