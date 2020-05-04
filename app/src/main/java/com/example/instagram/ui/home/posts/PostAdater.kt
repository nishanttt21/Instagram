package com.example.instagram.ui.home.posts

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.ui.base.BaseAdapter

class PostAdapter(
    parentLifecycle: Lifecycle,
    postList: ArrayList<PostData>,
    val listener: PostItemViewHolder.HandlePostClicks
) : BaseAdapter<PostData, PostItemViewHolder>(parentLifecycle, postList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder =
        PostItemViewHolder(parent, listener)
}
