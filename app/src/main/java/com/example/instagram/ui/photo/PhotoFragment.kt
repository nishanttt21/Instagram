package com.example.instagram.ui.photo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.databinding.FragmentPhotoBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.main.MainSharedViewModel
import com.mindorks.paracamera.Camera
import timber.log.Timber
import java.io.FileNotFoundException
import javax.inject.Inject

class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {

    companion object {

        internal const val TAG = "PhotoFragment"
        private const val RESULT_GALLERY_PICK = 1001
        fun newInstance(): PhotoFragment {
            val args = Bundle()
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }

    }

    @Inject
    lateinit var camera: Camera

    @Inject
    lateinit var sharedViewModel: MainSharedViewModel

    override fun provideLayoutId(): Int = R.layout.fragment_photo

    override fun setupView() {
        binding.viewCamera.setOnClickListener {
            try {
                camera.takePicture()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        binding.viewGallery.setOnClickListener {
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }.also {
                startActivityForResult(it, Companion.RESULT_GALLERY_PICK)
            }
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.loadingProgressBar.observe(this, Observer {
            binding.pbLoading.visibility = if (it == true) View.VISIBLE else View.GONE
        })
        viewModel.postData.observe(this, Observer {
            it?.getIfNotHandled()?.run {
                sharedViewModel.setNewPost(this)
                sharedViewModel.onHomeRedirect()
            }
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent):
            Unit = fragmentComponent.inject(this)

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RESULT_GALLERY_PICK -> {
                    try {
                        intent?.data?.let {
                            activity?.contentResolver?.openInputStream(it)?.run {
                                viewModel.onGalleryImageSelected(this)
                            }
                        } ?: showMessage(R.string.try_again)
                    } catch (e: FileNotFoundException) {
                        Timber.e(e)
                        showMessage(R.string.try_again)
                    }
                }
                Camera.REQUEST_TAKE_PHOTO -> {
                    viewModel.onCameraImageTaken { camera.cameraBitmapPath }
                }
            }
        }
    }
}
