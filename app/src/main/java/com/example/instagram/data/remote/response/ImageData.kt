package com.example.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageData(
    @Expose
    @SerializedName("imageUrl")
    var imageUrl: String
)

