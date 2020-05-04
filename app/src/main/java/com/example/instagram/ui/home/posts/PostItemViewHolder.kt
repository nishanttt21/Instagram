package com.example.instagram.ui.home.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instagram.R
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.databinding.ItemViewPostBinding
import com.example.instagram.di.component.ViewHolderComponent
import com.example.instagram.ui.base.BaseItemViewHolder
import com.example.instagram.utils.common.GlideHelper

class PostItemViewHolder(parent: ViewGroup, val listener: HandlePostClicks) :
    BaseItemViewHolder<ItemViewPostBinding, PostData, PostItemViewModel>(
        ItemViewPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) {
    interface HandlePostClicks {
        fun onPostClick(postId: String)
    }

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent):
            Unit = viewHolderComponent.inject(this)

    override fun setupView() {
        binding.ivLike.setOnClickListener {
            viewModel.onLikeClick()
        }
        binding.ivPost.setOnClickListener {
            listener.onPostClick(viewModel.postId.value ?: "random")
        }
    }


    override fun setupObservers() {
        super.setupObservers()
        viewModel.name.observe(this, Observer {
            binding.tvName.text = it
        })
        viewModel.isLiked.observe(this, Observer {
            binding.ivLike.setImageResource(
                if (it) R.drawable.ic_heart_selected
                else R.drawable.ic_heart_unselected
            )
        })
        viewModel.likesCount.observe(this, Observer {
            binding.tvLikesCount.run {
                text = this.context.resources.getString(R.string.post_like_label, it)
            }
        })
        viewModel.postTime.observe(this, Observer {
            binding.tvTime.text = it
        })
        viewModel.profileImage.observe(this, Observer {
            it?.run {
                val glideRequest = Glide.with(binding.ivProfilePic.context)
                    .load(GlideHelper.getProtectedUrl(url, headers))
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_selected))
                if (placeHolderHeight > 0 && placeHolderWidth > 0) {
                    val params = binding.ivProfilePic.layoutParams as ViewGroup.LayoutParams
                    params.width = placeHolderWidth
                    params.height = placeHolderHeight
                    binding.ivProfilePic.layoutParams = params
                    glideRequest.apply {
                        RequestOptions.overrideOf(placeHolderWidth, placeHolderHeight)
                        RequestOptions.placeholderOf(R.drawable.ic_profile_unselected)
                    }
                }
                glideRequest.into(binding.ivProfilePic)
            }
        })
        viewModel.imageDetail.observe(this, Observer {
            it?.run {
                val glideRequest = Glide.with(binding.ivPost.context)
                    .load(GlideHelper.getProtectedUrl(url, headers))
                if (placeHolderHeight > 0 && placeHolderWidth > 0) {
                    val params = binding.ivPost.layoutParams as ViewGroup.LayoutParams
                    params.width = placeHolderWidth
                    params.height = placeHolderHeight
                    binding.ivPost.layoutParams = params
                    glideRequest.apply {
                        RequestOptions.overrideOf(placeHolderWidth, placeHolderHeight)
                        RequestOptions.placeholderOf(R.drawable.ic_gallery)
                    }
                }
                glideRequest.into(binding.ivPost)
            }
        })
    }
}