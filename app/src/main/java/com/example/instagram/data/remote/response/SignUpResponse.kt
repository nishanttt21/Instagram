package com.example.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @Expose
    @SerializedName("accessToken")
    var accessToken: String,
    @Expose
    @SerializedName("refreshToken")
    var refreshToken: String,
    @Expose
    @SerializedName("userId")
    var userId: String,
    @Expose
    @SerializedName("userName")
    var userName: String,
    @Expose
    @SerializedName("userEmail")
    var userEmail: String
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
