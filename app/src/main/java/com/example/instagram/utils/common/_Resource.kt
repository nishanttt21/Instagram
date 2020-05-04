package com.example.instagram.utils.common

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
data class _Resource<out T> constructor(
    val status: ResourceState,
    val data: T? = null,
    val message: String = ""
)

sealed class ResourceState {
    object Loading : ResourceState()
    object Success : ResourceState()
    object Stale : ResourceState()
    object Error : ResourceState()
    object RequestError : ResourceState()
    object Unknown : ResourceState()
}