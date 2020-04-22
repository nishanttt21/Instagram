package com.example.instagram.ui.photo

import android.os.Bundle
import android.view.View
import com.example.instagram.R
import com.example.instagram.databinding.FragmentPhotoBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment

class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {

    companion object {

        internal const val TAG = "PhotoFragment"

        fun newInstance(): PhotoFragment {
            val args = Bundle()
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_photo

    override fun setupView(view: View) {
//        TODO("Not yet implemented")
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)
}