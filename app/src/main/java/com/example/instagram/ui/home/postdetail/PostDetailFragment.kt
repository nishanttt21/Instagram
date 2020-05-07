package com.example.instagram.ui.home.postdetail

import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instagram.R
import com.example.instagram.databinding.FragmentPostDetailBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.home.profiledetail.ProfileDetailFragment
import com.example.instagram.utils.common.GlideHelper

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding, PostDetailViewModel>() {

    companion object {
        const val POST_ID = "postId"
        const val TAG = "PostDetailFragment"
    }

    val args: PostDetailFragmentArgs by navArgs()
    val postId :String by lazy { args.postId }
    override fun provideLayoutId(): Int = R.layout.fragment_post_detail

    override fun setupView() {
        registerForContextMenu(binding.ivPost)
        viewModel.getPostDetail(postId)
        binding.ivLike.setOnClickListener {
            viewModel.onLikeClick()
        }
        binding.ivProfilePic.setOnClickListener {
            navigateToProfileDetailFragment()
        }
        binding.tvName.setOnClickListener {
            navigateToProfileDetailFragment()
        }

    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = MenuInflater(requireContext())
        inflater.inflate(R.menu.menu_post_options, menu)
        menu.setHeaderTitle("Item will Added soon")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deletePost){
            viewModel.deletePost(postId)
            return true
        }else return super.onContextItemSelected(item)
    }

    private fun navigateToProfileDetailFragment() {
        val bundle = bundleOf(
            ProfileDetailFragment.USER_NAME to viewModel.name.value,
            ProfileDetailFragment.USER_Profile to viewModel.profilePicUrl.value
        )
        findNavController().navigate(R.id.profileDetailFragment, bundle)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.status.observe(this, Observer {
            if (it == true){
                showSnackBar("Deleted")
            }
        })
        viewModel.name.observe(this, Observer {
            binding.tvName.text = it
            binding.toolbar.title = String.format(getString(R.string.user_id_formate), it)
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

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)
}