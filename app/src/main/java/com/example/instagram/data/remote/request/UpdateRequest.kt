package com.example.instagram.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateRequest(
    @Expose
    @SerializedName("name")
    val name: String = "",
    @Expose
    @SerializedName("profilePicUrl")
    val profilePicUrl: String? = "",
    @Expose
    @SerializedName("tagline")
    val tagline: String = ""

)