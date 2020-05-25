package com.example.instagram.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.remote.response.PostData
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.home.posts.PostAdapter
import com.example.instagram.ui.home.posts.PostItemViewHolder
import com.example.instagram.ui.home.profiledetail.ProfileDetailActivity
import com.example.instagram.ui.main.MainSharedViewModel
import com.example.instagram.ui.postdetail.PostDetailActivity
import com.example.instagram.utils.common.ManagePermission
import com.mindorks.paracamera.Camera
import timber.log.Timber
import javax.inject.Inject

private const val CAMERA_PERMISSION_REQUEST = 1001
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {

        internal const val TAG = "HomeFragment"
        private val requiresPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
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
    lateinit var camera: Camera

    private lateinit var postAdapter: PostAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun setupView() {
        postAdapter =
            PostAdapter(lifecycle, ArrayList(), object : PostItemViewHolder.HandlePostClicks {
                override fun onPostClick(postId: String?) {
                    if (postId.isNullOrEmpty()) {
                        showSnackBar(R.string.err_post_detail)
                    } else {
                        startActivity(PostDetailActivity.getStartIntent(requireContext(),postId))
                    }
                }

                override fun onProfileClick(user: PostData.User?) {
                    user?.let {
                        startActivity(ProfileDetailActivity.getStartIntent(requireActivity(),user.name,user.profilePicUrl))
                    } ?: showSnackBar(R.string.err_user_detail)
                }
            })
        binding.ivCamera.setOnClickListener {
            try {
                if (ManagePermission.requestPermissions(requireActivity(), requiresPermission ,
                        CAMERA_PERMISSION_REQUEST).hasPermissions())
                    camera.takePicture()
            } catch (e: Exception) {
                Timber.e(e)
            }

        }
        binding.ivInstaDirect.setOnClickListener {
            showSnackBar("Coming Soon")
        }

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Camera.REQUEST_TAKE_PHOTO -> {
                    viewModel.onCameraImageTaken { camera.cameraBitmapPath }
                }
            }
        }
    }
}