package com.example.instagram.ui.profile

import android.os.Bundle
import android.view.View
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment

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

    override fun setupView(view: View) {
//        TODO("Not yet implemented")
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}