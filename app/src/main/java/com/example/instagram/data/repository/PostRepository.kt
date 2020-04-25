package com.example.instagram.data.repository

import com.example.instagram.data.model.Dummy
import com.example.instagram.data.model.Post
import com.example.instagram.data.model.User
import io.reactivex.Single

interface PostRepository {

    fun fetchDummy(id: String): Single<List<Dummy>>

    fun fetchHomePostListCall(firstPostId: String?, lastPostId: String?, user: User):
            Single<List<Post>>

    fun makeLikePost(post: Post, user: User):
            Single<Post>

    fun makeUnlikePost(post: Post, user: User):
            Single<Post>

    fun createPost(imageUrl: String, imageWidth: Int, imageHeight: Int, user: User): Single<Post>
}