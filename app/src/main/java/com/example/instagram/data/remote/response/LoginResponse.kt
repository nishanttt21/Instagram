package com.example.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose
    @SerializedName("accessToken")
    val accessToken: String,
    @Expose
    @SerializedName("refreshToken")
    val refreshToken: String,
    @Expose
    @SerializedName("userId")
    val userId: String,
    @Expose
    @SerializedName("userName")
    val userName: String,
    @Expose
    @SerializedName("userEmail")
    val userEmail: String,
    @Expose
    @SerializedName("profilePicUrl")
    val profilePicUrl: String?
)


/*

{
    "statusCode": "success",
    "status": 200,
    "message": "",
    "accessToken": "",
    "refreshToken": "",
    "userId": "",
    "userName": "",
    "userEmail": ""
}*/
