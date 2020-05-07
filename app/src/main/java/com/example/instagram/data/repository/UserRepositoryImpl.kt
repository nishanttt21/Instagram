package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.local.prefs.UserPreferences
import com.example.instagram.data.model.Me
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.request.LoginRequest
import com.example.instagram.data.remote.request.SignUpRequest
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
        userPreferences.setUserProfilePic(user.profilePicUrl)
        userPreferences.setAccessToken(user.accessToken)
    }

    override fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserProfile()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
    }

    override fun getCurrentUser(): User? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val accessToken = userPreferences.getAccessToken()
        val profilePic = userPreferences.getUserProfile()

        return if (userId !== null && userName != null && userEmail != null && accessToken != null && profilePic != null)
            User(userId, userName, userEmail, accessToken,profilePic)
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
                    refreshToken = refreshToken
                )
            }
        }

    override fun doSignUpCall(name: String, email: String, password: String):
            Single<User> =
        networkService.doSignUpCall(SignUpRequest(email = email, password = password, name = name))
            .map {
                it.run {
                    return@map User(
                        id = userId,
                        name = userName,
                        email = userEmail,
                        accessToken = accessToken,
                        refreshToken = refreshToken
                    )
                }
            }

    override fun doSignOutCall():
            Single<GeneralResponse> = networkService.doSignOutCall(
        accessToken = userPreferences.getAccessToken()!!,
        userId = userPreferences.getUserId()!!
    )

    override fun fetchMyInfo():
            Single<Me> =
        networkService.fetchMyInfo(
            accessToken = userPreferences.getAccessToken()!!,
            userId = userPreferences.getUserId()!!
        ).map { it.data }

    override fun updateMyInfo(me: Me):
            Single<GeneralResponse> = networkService.updateMyInfo(me,
        accessToken = userPreferences.getAccessToken()!!, userId = userPreferences.getUserId()!!
    )
}
