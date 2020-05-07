package com.example.instagram.ui.profile.mypost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instagram.R
import com.example.instagram.data.model.Post
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.databinding.ItemViewMyPostBinding
import com.example.instagram.databinding.ItemViewPostBinding
import com.example.instagram.di.component.ViewHolderComponent
import com.example.instagram.ui.base.BaseItemViewHolder
import com.example.instagram.utils.common.GlideHelper

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
        binding.ivPost.setOnClickListener { listener.onPostClick(viewModel.post.value) }
    }


    override fun setupObservers() {
        super.setupObservers()
        viewModel.imageDetail.observe(this, Observer {
            Glide.with(binding.ivPost).load(it).placeholder(R.drawable.ic_person).into(binding.ivPost)
        })
//        viewModel.imageDetail.observe(this, Observer {
//            it?.run {
//                val glideRequest = Glide.with(binding.ivPost.context)
//                    .load(GlideHelper.getProtectedUrl(url, headers))
//                if (placeHolderHeight > 0 && placeHolderWidth > 0) {
//                    val params = binding.ivPost.layoutParams as ViewGroup.LayoutParams
//                    params.width = placeHolderWidth
//                    params.height = placeHolderHeight
//                    binding.ivPost.layoutParams = params
//                    glideRequest.apply {
//                        RequestOptions.overrideOf(placeHolderWidth, placeHolderHeight)
//                        RequestOptions.placeholderOf(R.drawable.ic_gallery)
//                    }
//                }
//                glideRequest.into(binding.ivPost)
//            }
//        })
    }
}