package com.example.instagram.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("userId")
    val id: String,
    @Expose
    @SerializedName("userName")
    var name: String,
    @Expose
    @SerializedName("userEmail")
    var email: String,
    @Expose
    @SerializedName("accessToken")
    val accessToken: String,
    @Expose
    @SerializedName("tagline")
    var tagline: String = "",
    @Expose
    @SerializedName("profilePicUrl")
    var profilePicUrl: String? = null
)