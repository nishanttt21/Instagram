package com.example.instagram.data.remote

import com.example.instagram.data.remote.request.*
import com.example.instagram.data.remote.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {


    @POST(Endpoints.DUMMY)
    fun doDummyCall(
        @Body request: DummyRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<DummyResponse>

    @POST(Endpoints.LOGIN)
    fun doLoginCall(
        @Body request: LoginRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<LoginResponse>

    @POST(Endpoints.SIGN_UP)
    fun doSignUpCall(
        @Body request: SignUpRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<SignUpResponse>

    @GET(Endpoints.HOME_POST_LIST)
    fun doHomePostListCall(
        @Query("firstPostId") firstPostId: String?,
        @Query("lastPostId") lastPostId: String?,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<PostListResponse>

    @PUT(Endpoints.POST_LIKE)
    fun doPostLikeCall(
        @Body request: PostLikeModifyRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<GeneralResponse>

    @PUT(Endpoints.POST_UNLIKE)
    fun doPostUnLikeCall(
        @Body request: PostLikeModifyRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<GeneralResponse>

    @DELETE(Endpoints.SIGN_OUT)
    fun doSignOutCall(
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<GeneralResponse>

    @GET(Endpoints.ME)
    fun fetchMyInfo(
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<MyResponse>

    @PUT(Endpoints.ME)
    fun updateMyInfo(
        @Body request: UpdateRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<GeneralResponse>

    @Multipart
    @POST(Endpoints.UPLOAD_IMAGE)
    fun doUploadImage(
        @Part image: MultipartBody.Part,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY
    ): Single<ImageResponse>

    @POST(Endpoints.CREATE_POST)
    fun doPostCreate(
        @Body request: PostCreationRequest,
        @Header(Networking.HEADER_USER_ID) userId: String, // pass using UserRepository
        @Header(Networking.HEADER_ACCESS_TOKEN) accessToken: String, // pass using UserRepository
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<PostCreationResponse>
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