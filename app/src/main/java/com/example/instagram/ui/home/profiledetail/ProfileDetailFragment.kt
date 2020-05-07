package com.example.instagram.ui.home.profiledetail

import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileDetailBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment

/**
 * Created by @author Deepak Dawade on 5/7/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class ProfileDetailFragment : BaseFragment<FragmentProfileDetailBinding, ProfileDetailViewModel>() {

    override fun provideLayoutId(): Int = R.layout.fragment_profile_detail

    override fun setupView() {
        val username = arguments?.getString(USER_NAME)
        val profilepic = arguments?.getString(USER_Profile)
        binding.apply {
            username?.let {
                toolbar.title = username
                tvUserName.text = username
            }
            profilepic?.let {
                Glide.with(ivProfilePic.context)
                    .load(it).placeholder(R.drawable.ic_person)
                    .into(ivProfilePic)
            }
            tvUserBio.text = getString(R.string.default_user_bio)
        }
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    companion object {
        const val USER_NAME = "username"
        const val USER_Profile = "userProfile"
        private const val TAG = "ProfileDetailFragment"
    }
}