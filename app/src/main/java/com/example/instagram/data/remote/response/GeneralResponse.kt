package com.example.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @Expose
    @SerializedName("statusCode")
    val statusCode: String,
    @Expose
    @SerializedName("status")
    val status: Int,
    @Expose
    @SerializedName("message")
    val message: String
)