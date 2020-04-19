package com.example.instagram.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseItemViewModel<T : Any>(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    private val _data = MutableLiveData<T>()
    val data: LiveData<T>
        get() = _data

    fun updateData(data: T) {
        _data.value = data
    }

    fun onManualCleared() = onCleared()
}