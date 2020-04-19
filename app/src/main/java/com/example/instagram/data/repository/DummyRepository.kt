package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.model.Dummy
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.request.DummyRequest
import io.reactivex.Single
import javax.inject.Inject

class DummyRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

}