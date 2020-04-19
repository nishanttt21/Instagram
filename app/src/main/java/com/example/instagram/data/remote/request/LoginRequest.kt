package com.example.instagram.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @Expose
    @SerializedName("email")
    var email: String,
    @Expose
    @SerializedName("password")
    val password: String
)

//{ "email": "","password": ""}
