package com.example.instagram.data.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by @author Deepak Dawade on 5/3/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
data class AuthenticationResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,
    @Expose
    @SerializedName("status")
    var status: Int,
    @Expose
    @SerializedName("message")
    var message: String,
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