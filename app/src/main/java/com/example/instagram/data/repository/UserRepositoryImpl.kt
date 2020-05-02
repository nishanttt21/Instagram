package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.local.prefs.UserPreferences
import com.example.instagram.data.model.Me
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.request.LoginRequest
import com.example.instagram.data.remote.request.SignUpRequest
import com.example.instagram.data.remote.request.UpdateRequest
import com.example.instagram.data.remote.response.GeneralResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) : UserRepository {

    override fun saveCurrentUser(user: User) {
        userPreferences.setUserId(user.id)
        userPreferences.setUserName(user.name)
        userPreferences.setUserEmail(user.email)
        userPreferences.setAccessToken(user.accessToken)
    }

    override fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
    }

    override fun getCurrentUser(): User? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val accessToken = userPreferences.getAccessToken()

        return if (userId !== null && userName != null && userEmail != null && accessToken != null)
            User(userId, userName, userEmail, accessToken)
        else
            null
    }

    override fun doLoginCall(email: String, password: String):
            Single<User> = networkService.doLoginCall(LoginRequest(email, password))
        .map {
            it.run {
                User(
                    id = userId,
                    name = userName,
                    email = userEmail,
                    accessToken = accessToken,
                    profilePicUrl = profilePicUrl
                )
            }
        }

    override fun doSignUpCall(name: String, email: String, password: String):
            Single<User> =
        networkService.doSignUpCall(SignUpRequest(email = email, password = password, name = name))
            .map {
                it.data.run {
                    User(
                        id = userId,
                        name = userName,
                        email = userEmail,
                        accessToken = accessToken
                    )
                }
            }

    override fun doSignOutCall():
            Single<GeneralResponse> =
        networkService.doSignOutCall(
            accessToken = getCurrentUser()!!.accessToken,
            userId = getCurrentUser()!!.id
        )

    override fun fetchMyInfo():
            Single<Me> =
        networkService.fetchMyInfo(
            accessToken = getCurrentUser()!!.accessToken,
            userId = getCurrentUser()!!.id
        ).map { it.data }

    override fun updateMyInfo(user: User):
            Single<GeneralResponse> = networkService.updateMyInfo(
        UpdateRequest(name = user.name, profilePicUrl = user.profilePicUrl, tagline = user.tagline)
        , accessToken = user.accessToken, userId = user.id
    )
}
