package com.example.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


data class PostCreationResponse(
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
    @SerializedName("data")
    var data: PostData
) {
    data class PostData(
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
}
