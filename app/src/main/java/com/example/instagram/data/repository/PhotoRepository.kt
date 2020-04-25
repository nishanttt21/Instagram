package com.example.instagram.data.repository

import com.example.instagram.data.model.User
import io.reactivex.Single
import java.io.File

interface PhotoRepository {
    fun uploadPhoto(imageFile: File, user: User): Single<String>
}
