package com.example.instagram.data.remote

import com.example.instagram.data.model.Me
import com.example.instagram.data.model.Post
import com.example.instagram.data.remote.request.*
import com.example.instagram.data.remote.response.*
import com.example.instagram.data.repository.AuthenticationResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {

    //0.Dummy Call
    @POST(Endpoints.DUMMY)
    fun doDummyCall(
        @Body request: DummyRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<DummyResponse>

    //1.Signup call
    @POST(Endpoints.SIGN_UP)
    fun doSignUpCall(
        @Body request: SignUpRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<AuthenticationResponse>

    //2.Login call
    @POST(Endpoints.LOGIN)
    fun doLoginCall(
        @Body request: LoginRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<AuthenticationResponse>

    //3.Logout
    @DELETE(Endpoints.SIGN_OUT)
    fun doSignOutCall(
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<GeneralResponse>

    //4.Fetch My Info
    @GET(Endpoints.ME)
    fun fetchMyInfo(
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<MyResponse<Me>>

    //5.Update my Info
    @PUT(Endpoints.ME)
    fun updateMyInfo(
        @Body request: Me,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<GeneralResponse>

    //6.Upload Image
    @Multipart
    @POST(Endpoints.UPLOAD_IMAGE)
    fun doUploadImage(
        @Part image: MultipartBody.Part,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<MyResponse<ImageData>>

    //7.Get Image
    //the url of the image in the instagram posts

    //8.Post Creation
    @POST(Endpoints.CREATE_POST)
    fun doPostCreate(
        @Body request: PostCreationRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<MyResponse<PostData>>

    //9.Post Detail
    @GET(Endpoints.POST_DETAILS)
    fun fetchPostDetail(
        @Path("postId") postId: String,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<MyResponse<PostData>>

    //10.Post Delete
    @DELETE(Endpoints.DELETE_POST)
    fun doPostDelete(
        @Path("postId") postId: String,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<GeneralResponse>

    //11.My Post List
    @GET(Endpoints.MY_POST_LIST)
    fun getMyPostList(
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<MyResponse<List<Post>>>

    //12.All Post List
    @GET(Endpoints.HOME_POST_LIST)
    fun doHomePostListCall(
        @Query("firstPostId") firstPostId: String?,
        @Query("lastPostId") lastPostId: String?,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<MyResponse<List<PostData>>>

    //13.Like a Post
    @PUT(Endpoints.POST_LIKE)
    fun doPostLikeCall(
        @Body request: PostLikeModifyRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<GeneralResponse>

    //14.Unlike a Post
    @PUT(Endpoints.POST_UNLIKE)
    fun doPostUnLikeCall(
        @Body request: PostLikeModifyRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<GeneralResponse>

    /*
     * Example to add other headers
     *
     *  @POST(Endpoints.DUMMY_PROTECTED)
        fun sampleDummyProtectedCall(
            @Body request: DummyRequest,
            @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
            @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
            @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
        ): Single<DummyResponse>
     */
}