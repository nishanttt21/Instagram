package com.example.instagram.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


data class Post(
    @Expose
    @SerializedName("id")
    var id: String,
    @Expose
    @SerializedName("imgUrl")
    var imgUrl: String,
    @Expose
    @SerializedName("imgWidth")
    var imgWidth: Int,
    @Expose
    @SerializedName("imgHeight")
    var imgHeight: Int,
    @Expose
    @SerializedName("createdAt")
    var createdAt: Date
)
