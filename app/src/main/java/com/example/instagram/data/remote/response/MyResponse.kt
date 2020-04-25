package com.example.instagram.data.remote.response

import com.example.instagram.data.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("message")
    var message: String,
    @Expose
    @SerializedName("data")
    val data: User
)