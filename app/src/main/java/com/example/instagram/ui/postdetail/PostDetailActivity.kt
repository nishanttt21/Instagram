package com.example.instagram.ui.postdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instagram.R
import com.example.instagram.databinding.ActivityPostDetailBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.home.profiledetail.ProfileDetailActivity
import com.example.instagram.utils.common.GlideHelper

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding, PostDetailViewModel>() {

    companion object {
        private const val POST_ID = "postId"
        private const val TAG = "PostDetailFragment"
        fun getStartIntent(
            context: Context, postId: String
        ): Intent {
            return Intent(context, PostDetailActivity::class.java).apply {
                putExtra(POST_ID, postId)
            }
        }
    }

    val postId :String by lazy { intent.getStringExtra(POST_ID) }
    override fun provideLayoutId(): Int = R.layout.activity_post_detail
private fun convertToPx(value:Int):Int{
    val r = resources
    val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,value.toFloat(),r.displayMetrics
    ).toInt()
    return px
}
    override fun setupView(savedInstanceState: Bundle?) {
        registerForContextMenu(binding.ivPost)
        viewModel.getPostDetail(postId)
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
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
        val inflater: MenuInflater = menuInflater
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
        startActivity(ProfileDetailActivity.getStartIntent(this,viewModel.name.value?:"",viewModel.profilePicUrl.value))
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

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)
}