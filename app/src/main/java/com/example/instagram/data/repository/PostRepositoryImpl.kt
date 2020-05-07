package com.example.instagram.data.repository

import com.example.instagram.data.local.DatabaseService
import com.example.instagram.data.model.Dummy
import com.example.instagram.data.model.Post
import com.example.instagram.data.model.User
import com.example.instagram.data.remote.NetworkService
import com.example.instagram.data.remote.request.DummyRequest
import com.example.instagram.data.remote.request.PostCreationRequest
import com.example.instagram.data.remote.request.PostLikeModifyRequest
import com.example.instagram.data.remote.response.PostData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) : PostRepository {

    override fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

    override fun fetchMyPostListCall(user: User):
            Single<List<Post>> =
        networkService.getMyPostList(userId = user.id, accessToken = user.accessToken)
            .map {
                it.data
            }

    override fun fetchHomePostListCall(firstPostId: String?, lastPostId: String?, user: User):
            Single<List<PostData>> =
        networkService.doHomePostListCall(
            firstPostId = firstPostId,
            lastPostId = lastPostId,
            userId = user.id,
            accessToken = user.accessToken
        )
            .map {
                it.data
            }

    override fun makeLikePost(postData: PostData, user: User):
            Single<PostData> =
        networkService.doPostLikeCall(
            PostLikeModifyRequest(postData.id),
            user.id,
            user.accessToken
        ).map {
            postData.likedBy?.apply {
                this.find { postUser -> postUser.id == user.id } ?: this.add(
                    PostData.User(
                        user.id,
                        user.name,
                        user.profilePicUrl
                    )
                )
            }
            return@map postData
        }


    override fun makeUnlikePost(postData: PostData, user: User):
            Single<PostData> =
        networkService.doPostUnLikeCall(
            PostLikeModifyRequest(postId = postData.id),
            userId = user.id,
            accessToken = user.accessToken
        ).map {
            postData.likedBy?.apply {
                find { postUser -> postUser.id == user.id }?.let { remove(it) }
            }
            return@map postData
        }

    override fun createPost(imageUrl: String, imageWidth: Int, imageHeight: Int, user: User):
            Single<PostData> = networkService.doPostCreate(
        PostCreationRequest(imageUrl, imageWidth, imageHeight), user.id, user.accessToken
    ).map {
        it.data.let {
            it.run {
                PostData(
                    id,
                    imgUrl,
                    imgWidth,
                    imgHeight,
                    createdAt,
                    creator = PostData.User(
                        id = user.id,
                        name = user.name,
                        profilePicUrl = user.profilePicUrl
                    ),
                    likedBy = mutableListOf()
                )

            }
        }
    }

    override fun getPostDetail(postId: String, user: User):
            Single<PostData> =
        networkService.fetchPostDetail(postId, user.id, user.accessToken).map {
            it.data
        }
}
