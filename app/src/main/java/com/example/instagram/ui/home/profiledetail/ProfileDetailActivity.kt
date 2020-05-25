package com.example.instagram.ui.home.profiledetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.ActivityProfileDetailBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity

/**
 * Created by @author Deepak Dawade on 5/7/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class ProfileDetailActivity : BaseActivity<ActivityProfileDetailBinding, ProfileDetailViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_profile_detail

    companion object {
        const val USER_NAME = "username"
        const val USER_Profile = "userProfile"
        private const val TAG = "ProfileDetailFragment"
        fun getStartIntent(context: Context, username: String, userProfile: String?): Intent {
            val intent = Intent(context, ProfileDetailActivity::class.java)
            intent.putExtra(USER_NAME, username)
            intent.putExtra(USER_Profile, userProfile)
            return intent
        }
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val username = intent?.getStringExtra(USER_NAME)
        val profilepic = intent?.getStringExtra(USER_Profile)
        binding.apply {
            username?.let {
                toolbar.title = username
                tvUserName.text = username
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            profilepic?.let {
                Glide.with(ivProfilePic.context)
                        .load(it).placeholder(R.drawable.ic_person)
                        .into(ivProfilePic)
            }
            tvUserBio.text = getString(R.string.default_user_bio)
        }
    }
    override fun injectDependencies(activityComponent: ActivityComponent)  = activityComponent.inject(this)

}