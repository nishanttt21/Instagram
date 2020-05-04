package com.example.instagram.ui.myactivity

import com.example.instagram.R
import com.example.instagram.databinding.FragmentMyActivityBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class MyActivityFragment : BaseFragment<FragmentMyActivityBinding, MyActivityViewModel>() {
    override fun provideLayoutId(): Int = R.layout.fragment_my_activity

    override fun setupView() {
//        TODO("Not yet implemented")
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)
}