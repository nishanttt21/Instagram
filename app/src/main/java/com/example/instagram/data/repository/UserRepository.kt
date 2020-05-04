package com.example.instagram.data.repository

import com.example.instagram.data.model.Me
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.response.GeneralResponse
import io.reactivex.Single

interface UserRepository {
    fun saveCurrentUser(user: User)
    fun removeCurrentUser()
    fun getCurrentUser(): User?
    fun doLoginCall(email: String, password: String): Single<User>
    fun doSignUpCall(name: String, email: String, password: String): Single<User>
    fun doSignOutCall(): Single<GeneralResponse>
    fun fetchMyInfo(): Single<Me>
    fun updateMyInfo(me: Me): Single<GeneralResponse>
}
