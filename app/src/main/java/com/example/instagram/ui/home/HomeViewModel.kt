package com.example.instagram.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.ui.post.Post
import com.example.instagram.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    compositeDisposable: CompositeDisposable,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService,
    networkHelper: NetworkHelper
) : BaseViewModel(compositeDisposable, networkHelper) {


    private val _data = MutableLiveData<List<Post>>()
    val data: LiveData<List<Post>>
        get() = _data

    override fun onCreate() {
        _data.value = listOf(
            Post("Test 1"),
            Post("Test 2"),
            Post("Test 3"),
            Post("Test 4"),
            Post("Test 5"),
            Post("Test 6"),
            Post("Test 7"),
            Post("Test 8"),
            Post("Test 9")
        )
    }
}