package com.example.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

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
        var createdAt: Date,
        @Expose
        @SerializedName("user")
        val creator: User,

        @Expose
        @SerializedName("likedBy")
        val likedBy: MutableList<User>?
) {

        data class User(
                @Expose
                @SerializedName("id")
                val id: String,

                @Expose
                @SerializedName("name")
                val name: String,

                @Expose
                @SerializedName("profilePicUrl")
                val profilePicUrl: String?
        )
}
