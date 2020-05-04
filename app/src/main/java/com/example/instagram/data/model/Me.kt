package com.example.instagram.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by @author Deepak Dawade on 5/3/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
data class Me(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("tagline")
    var tagline: String,
    @Expose
    @SerializedName("profilePicUrl")
    var profilePicUrl: String?
)