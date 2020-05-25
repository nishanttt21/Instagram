package com.example.instagram.ui.profile.mypost

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentMyPostBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.postdetail.PostDetailActivity
import com.example.instagram.ui.profile.mypost.mypostadapter.MyPostAdapter
import com.example.instagram.ui.profile.mypost.mypostadapter.MyPostItemViewHolder

/**
 * Created by @author Deepak Dawade on 5/20/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
class MyPostFragment : BaseFragment<FragmentMyPostBinding, MyPostViewModel>() {
    override fun provideLayoutId(): Int = R.layout.fragment_my_post

    private lateinit var myPostAdapter: MyPostAdapter
    override fun onStart() {
        super.onStart()
        viewModel.fetchMyPostList()
    }

    override fun setupView() {
        myPostAdapter =
                MyPostAdapter(
                        lifecycle,
                        ArrayList(),
                        object : MyPostItemViewHolder.HandlePostItemClicks {
                            override fun onPostClick(postId: String?) {
                                postId?.let {
                                    startActivity(PostDetailActivity.getStartIntent(requireContext(), postId))
                                } ?: showSnackBar(R.string.err_post_detail)
                            }
                        })


        binding.rvMyPostList.apply {
            layoutManager = GridLayoutManager(requireContext(),3).apply {
                spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        val count = myPostAdapter.itemCount % 3
                        return if(position == myPostAdapter.itemCount-1) count else 1
                    }
                }
            }
            adapter = myPostAdapter
        }

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.myPosts.observe(this, Observer {
            it?.run {
                if (size > 0) {
                    binding.noPost.visibility = View.GONE
                    binding.rvMyPostList.visibility = View.VISIBLE
                    myPostAdapter.appendData(this)
                } else {
                    binding.noPost.visibility = View.VISIBLE
                    binding.rvMyPostList.visibility = View.GONE
                }
            } ?: myPostAdapter.appendData(arrayListOf())
        })
        viewModel.progress.observe(this, Observer {
            binding.progress.visibility = if (it == true) View.VISIBLE else View.GONE
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}
