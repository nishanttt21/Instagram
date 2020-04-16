package com.example.instagram.ui

import com.example.instagram.db.DataBaseService
import com.example.instagram.db.NetworkService
import javax.inject.Inject

class MainViewModel @Inject constructor (private val networkService: NetworkService, private val dataBaseService: DataBaseService) {
    fun getNetwork() = networkService.getData()
    fun getDatabase() = dataBaseService.getData()
}