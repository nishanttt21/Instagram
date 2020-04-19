package com.example.instagram.ui.post

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.ui.base.BaseItemViewModel
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PostViewModel @Inject constructor(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) : BaseItemViewModel<Post>(schedulerProvider, compositeDisposable, networkHelper) {


    override fun onCreate() {

    }

}