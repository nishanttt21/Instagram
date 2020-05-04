package com.example.instagram.ui.search

import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun provideLayoutId(): Int = R.layout.fragment_search

    override fun setupView() {
        //:TODO("Not yet implemented")
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)
}