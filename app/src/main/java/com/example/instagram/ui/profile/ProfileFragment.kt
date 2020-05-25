package com.example.instagram.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.loginsignup.LoginSignupActivity
import com.example.instagram.ui.profile.editprofile.EditProfileActivity
import com.example.instagram.ui.profile.profileadapter.ProfilePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    companion object {

        internal const val TAG = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun provideLayoutId(): Int = R.layout.fragment_profile
    override fun onStart() {
        super.onStart()
        viewModel.fetchMyInfo()
    }

    override fun setupView() {
        binding.ivOptions.setOnClickListener {
            if (binding.profileOptionLayout.isVisible)
                binding.profileOptionLayout.visibility = View.GONE
            else binding.profileOptionLayout.visibility = View.VISIBLE
        }
        binding.logoutOption.setOnClickListener {
            viewModel.onLogoutClick()
        }
        binding.pager.adapter = ProfilePagerAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.pager){tab, position ->
            when(position){
                0-> {
                    tab.icon = requireContext().getDrawable(R.drawable.ic_photo)
                    tab.text = "Posts"

                }
                1->{
                    tab.icon = requireContext().getDrawable(R.drawable.ic_bookmark)
                    tab.text = "Dummy"
                }
            }
        }.attach()

        binding.rootLayout.setOnClickListener {
            if (binding.profileOptionLayout.isVisible)
                binding.profileOptionLayout.visibility = View.GONE
        }
        binding.settingOption.setOnClickListener {
            showSnackBar("This Feature Available soon")
        }
        binding.savedOption.setOnClickListener {
            showSnackBar("This Feature Available soon")
        }
        binding.closeFriendOption.setOnClickListener {
            showSnackBar("This Feature Available soon")
        }
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.goToLogin.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(requireActivity(), LoginSignupActivity::class.java))
                requireActivity().finish()
            }

        })

        viewModel.myInfo.observe(this, Observer {
            binding.tvUserName.text = it.name
            binding.tvUserBio.text = it.tagline
            binding.tvUserId.text = String.format(getString(R.string.user_id_formate), it.name)
            it.profilePicUrl.let {
                binding.ivProfilePic.run {
                    Glide.with(this.context).load(it).placeholder(R.drawable.ic_person).into(this)
                }
            }
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}
