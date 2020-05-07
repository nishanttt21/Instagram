package com.example.instagram.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.home.posts.PostAdapter
import com.example.instagram.ui.home.posts.PostItemViewHolder
import com.example.instagram.ui.home.profiledetail.ProfileDetailFragment
import com.example.instagram.ui.main.MainSharedViewModel
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
    lateinit var sharedViewModel: MainSharedViewModel

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var postAdapter: PostAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun setupView() {
        postAdapter =
                PostAdapter(lifecycle, ArrayList(), object : PostItemViewHolder.HandlePostClicks {
                    override fun onPostClick(postId: String?) {
                        if (postId.isNullOrEmpty()) {
                            showSnackBar(R.string.err_post_detail)
                        } else {
                            val action = HomeFragmentDirections.actionHomeFragmentToPostDetailFragment(postId)
                            findNavController().navigate(action)
                        }
                    }

                    override fun onPostProfileClick(user: PostData.User?) {
                        user?.let {
                            val bundle = bundleOf(ProfileDetailFragment.USER_NAME to user.name,ProfileDetailFragment.USER_Profile to user.profilePicUrl)
                            findNavController().navigate(R.id.profileDetailFragment,bundle)
                        } ?: showSnackBar(R.string.err_user_detail)
                    }
                })
        binding.ivCamera.setOnClickListener {
            showSnackBar("Coming Soon")
        }
        binding.ivInstaDirect.setOnClickListener {
            showSnackBar("Coming Soon")
        }

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
        viewModel.postsData.observe(this, Observer {
            it.data?.run { postAdapter.appendData(this) }
        })
        viewModel.loading.observe(this, Observer {
            binding.progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
        sharedViewModel.newPost.observe(this, Observer {
            it.getIfNotHandled()?.run {
                viewModel.onNewPost(this)
            }
        })
        viewModel.refreshPosts.observe(this, Observer {
            it.data?.run {
                postAdapter.updateList(this)
                binding.rvPosts.scrollToPosition(0)
            }
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}