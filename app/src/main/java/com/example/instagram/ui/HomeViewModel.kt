package com.example.instagram.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    compositeDisposable: CompositeDisposable,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService,
    networkHelper: NetworkHelper
) : BaseViewModel(compositeDisposable, networkHelper) {


    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data

    override fun onCreate() {
        _data.value = networkService.getData()
    }
}