package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.local.prefs.UserPreferences
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.NetworkService
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) : PhotoRepository {
    override fun uploadPhoto(imageFile: File, user: User):
            Single<String> = MultipartBody.Part.createFormData(
        "image", imageFile.name,
        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    ).run {
        return@run networkService.doUploadImage(
            userId = user.id,
            accessToken = user.accessToken,
            image = this
        ).map {
            it.data.imageUrl
        }

    }


}