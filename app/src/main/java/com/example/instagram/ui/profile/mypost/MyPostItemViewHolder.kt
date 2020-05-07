package com.example.instagram.ui.profile.mypost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.data.model.Post
import com.example.instagram.databinding.ItemViewMyPostBinding
import com.example.instagram.di.component.ViewHolderComponent
import com.example.instagram.ui.base.BaseItemViewHolder

class MyPostItemViewHolder(parent: ViewGroup, val listener: HandlePostItemClicks) :
    BaseItemViewHolder<ItemViewMyPostBinding, Post, MyPostItemViewModel>(
        ItemViewMyPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) {
    interface HandlePostItemClicks {
        fun onPostClick(postId: String?)
    }

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent):
            Unit = viewHolderComponent.inject(this)

    override fun setupView() {
        binding.ivPost.setOnClickListener { listener.onPostClick(viewModel.data.value?.id) }
    }
    override fun setupObservers() {
        super.setupObservers()
        viewModel.image.observe(this, Observer {
            Glide.with(binding.ivPost.context).load(it).placeholder(R.drawable.ic_person)
                .into(binding.ivPost)
        })
    }
}