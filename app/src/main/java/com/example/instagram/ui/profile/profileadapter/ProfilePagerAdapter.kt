package com.example.instagram.ui.profile.profileadapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.ui.dummies.DummiesFragment
import com.example.instagram.ui.profile.ProfileFragment
import com.example.instagram.ui.profile.mypost.MyPostFragment

/**
 * Created by @author Deepak Dawade on 5/11/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class ProfilePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position){
            0-> MyPostFragment()
            1-> DummiesFragment()
            else -> ProfileFragment()
        }
    }
}
