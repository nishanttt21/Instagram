package com.example.instagram.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.home.posts.PostAdapter
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {

        internal const val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var postAdapter: PostAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun setupView(view: View) {
        binding.rvPosts.apply {
            layoutManager = linearLayoutManager
            adapter = postAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    layoutManager?.run {
                        if (this is LinearLayoutManager && itemCount in 0..findLastVisibleItemPosition() + 1)
                            viewModel.onLoadMore()
                    }
                }
            })
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.posts.observe(this, Observer {
            it.data?.run { postAdapter.appendData(this) }
        })
        viewModel.loading.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}