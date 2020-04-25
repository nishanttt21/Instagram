package com.example.instagram.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.login.LoginActivity
import com.example.instagram.ui.profile.editprofile.EditProfileActivity

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

    override fun setupView() {
        binding.tvLogout.setOnClickListener {
            viewModel.onLogoutClick()
        }
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchMyInfo()

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.goToLogin.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }

        })

        viewModel.myInfo.observe(this, Observer {
            binding.tvUserName.text = it.name
            binding.tvUserBio.text = it.tagline
            it.profilePicUrl?.let {
                binding.ivProfilePic.run {
                    Glide.with(this.context).load(it).placeholder(R.drawable.ic_person).into(this)
                }

            }
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}