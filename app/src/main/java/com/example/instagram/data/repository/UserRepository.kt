package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.local.prefs.UserPreferences
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.request.LoginRequest
import com.example.instagram.data.remote.request.SignUpRequest
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) {

    fun saveCurrentUser(user: User) {
        userPreferences.setUserId(user.id)
        userPreferences.setUserName(user.name)
        userPreferences.setUserEmail(user.email)
        userPreferences.setAccessToken(user.accessToken)
    }

    fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
    }

    fun getCurrentUser(): User? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val accessToken = userPreferences.getAccessToken()

        return if (userId !== null && userName != null && userEmail != null && accessToken != null)
            User(userId, userName, userEmail, accessToken)
        else
            null
    }

    fun doLoginCall(email: String, password: String):
            Single<User> = networkService.doLoginCall(LoginRequest(email, password))
        .map {
            User(
                id = it.userId,
                name = it.userName,
                email = it.userEmail,
                accessToken = it.accessToken,
                profilePicUrl = it.profilePicUrl
            )
        }

    fun doSignUpCall(name: String, email: String, password: String):
            Single<User> =
        networkService.doSignUpCall(SignUpRequest(email = email, password = password, name = name))
            .map {
                User(
                    id = it.userId,
                    name = it.userName,
                    email = it.userEmail,
                    accessToken = it.accessToken,
                    profilePicUrl = it.profilePicUrl
                )
            }
}