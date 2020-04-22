package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.model.Dummy
import com.example.instagram.data.model.Post
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.request.DummyRequest
import com.example.instagram.data.remote.request.PostLikeModifyRequest
import io.reactivex.Single
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

    fun fetchHomePostListCall(firstPostId: String?, lastPostId: String?, user: User):
            Single<List<Post>> =
        networkService.doHomePostListCall(
            firstPostId = firstPostId,
            lastPostId = lastPostId,
            userId = user.id,
            accessToken = user.accessToken
        )
            .map {
                it.data
            }

    fun makeLikePost(post: Post, user: User):
            Single<Post> =
        networkService.doPostLikeCall(
            PostLikeModifyRequest(post.id),
            user.id,
            user.accessToken
        ).map {
            post.likedBy?.apply {
                this.find { postUser -> postUser.id == user.id } ?: this.add(
                    Post.User(
                        user.id,
                        user.name,
                        user.profilePicUrl
                    )
                )
            }
            return@map post
        }


    fun makeUnlikePost(post: Post, user: User):
            Single<Post> =
        networkService.doPostUnLikeCall(
            PostLikeModifyRequest(postId = post.id),
            userId = user.id,
            accessToken = user.accessToken
        ).map {
            post.likedBy?.apply {
                find { postUser -> postUser.id == user.id }?.let { remove(it) }
            }
            return@map post
        }
}