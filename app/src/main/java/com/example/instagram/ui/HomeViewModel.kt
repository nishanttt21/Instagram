package com.example.instagram.ui

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.di.FragmentScope
import com.example.instagram.utils.NetworkHelper
import javax.inject.Inject

@FragmentScope
class HomeViewModel @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService,
    private val networkHelper: NetworkHelper
) {

    val someData: String
        get() = networkService.getData()
}