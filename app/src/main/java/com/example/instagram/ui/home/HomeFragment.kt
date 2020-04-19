package com.example.instagram.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.post.PostAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {

        val TAG = "HomeFragment"

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
        view.postRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = postAdapter
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.data.observe(this, Observer {
            postAdapter.appendData(it)
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}