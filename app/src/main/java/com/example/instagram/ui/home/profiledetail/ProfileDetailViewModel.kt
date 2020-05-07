package com.example.instagram.ui.home.profiledetail

import com.example.instagram.ui.base.BaseViewModel
import com.example.instagram.utils.network.NetworkHelper
import com.example.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 5/7/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class ProfileDetailViewModel @Inject constructor(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper) :BaseViewModel(schedulerProvider, compositeDisposable, networkHelper)  {
    override fun onCreate() {
        //TODO("Not yet implemented")
    }
}