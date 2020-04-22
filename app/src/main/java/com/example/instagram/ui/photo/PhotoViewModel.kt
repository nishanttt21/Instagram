package com.example.instagram.ui.photo

import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PhotoViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    override fun onCreate() {
//        TODO("Not yet implemented")
    }

}