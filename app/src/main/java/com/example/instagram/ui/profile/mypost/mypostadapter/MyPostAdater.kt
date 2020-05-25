package com.example.instagram.ui.profile.mypost.mypostadapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.instagram.data.model.Post
import com.example.instagram.ui.base.BaseAdapter

class MyPostAdapter(
    parentLifecycle: Lifecycle,
    postList: ArrayList<Post>,
    val listener: MyPostItemViewHolder.HandlePostItemClicks
) : BaseAdapter<Post, MyPostItemViewHolder>(parentLifecycle, postList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostItemViewHolder =
            MyPostItemViewHolder(parent, listener)
}
