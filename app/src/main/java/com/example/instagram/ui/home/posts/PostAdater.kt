package com.example.instagram.ui.home.posts

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.instagram.data.model.Post
import com.example.instagram.ui.base.BaseAdapter

class PostAdapter(
    parentLifecycle: Lifecycle,
    postList: ArrayList<Post>
) : BaseAdapter<Post, PostItemViewHolder>(parentLifecycle, postList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder =
        PostItemViewHolder(parent)
}
