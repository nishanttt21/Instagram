package com.example.instagram.data.repository

import com.example.instagram.data.model.Dummy
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.response.PostData
import io.reactivex.Single

interface PostRepository {

    fun fetchDummy(id: String): Single<List<Dummy>>

    fun fetchHomePostListCall(
        firstPostId: String?,
        lastPostId: String?,
        user: User
    ): Single<List<PostData>>

    fun makeLikePost(postData: PostData, user: User):
            Single<PostData>

    fun makeUnlikePost(postData: PostData, user: User):
            Single<PostData>

    fun createPost(
        imageUrl: String,
        imageWidth: Int,
        imageHeight: Int,
        user: User
    ): Single<PostData>
}