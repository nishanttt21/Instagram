package com.example.instagram.ui.post

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.instagram.ui.base.BaseAdapter

class PostAdater(
    parentLifecycle: Lifecycle,
    postList: ArrayList<Post>
) : BaseAdapter<Post, PostViewHolder>(parentLifecycle, postList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(parent)
}
