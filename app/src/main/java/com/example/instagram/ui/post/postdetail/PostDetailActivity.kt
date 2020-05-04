package com.example.instagram.ui.post.postdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.instagram.R
import com.example.instagram.databinding.ActivityPostDetailBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding, PostDetailViewModel>() {

    companion object {
        const val POST_ID = "postId"
        const val TAG = "PostDetailActivity"
        fun getStartIntent(
            context: Context, postId: String
        ): Intent {
            return Intent(context, PostDetailActivity::class.java).apply {
                putExtra(POST_ID, postId)
            }
        }
    }

    override fun provideLayoutId(): Int = R.layout.activity_post_detail

    override fun setupView(savedInstanceState: Bundle?) {
        val postId = intent.getStringExtra(POST_ID)
        binding.tvUserId.text = postId ?: "null"
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)
}