package com.example.instagram.ui.profile.editprofile

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.ActivityEditProfileBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.utils.common.ManagePermission
import com.mindorks.paracamera.Camera
import java.io.FileNotFoundException
import javax.inject.Inject

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {

    companion object {
        private const val RESULT_GALLERY_PICK = 1001
        private val cameraRequestCode: Int = 1000
        private val galleryRequestCode: Int = 1001
        private val requiresPermission = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }

    override fun provideLayoutId(): Int = R.layout.activity_edit_profile

    @Inject
    lateinit var camera: Camera

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        viewModel.fetchMyInfo()
        binding.ivCamera.setOnClickListener {
            if (ManagePermission.requestPermissions(
                            this,
                            requiresPermission,
                            galleryRequestCode
                    ).hasPermissions()
            )
                camera.takePicture()
        }
        binding.tvChangeProfile.setOnClickListener {
            if (ManagePermission.requestPermissions(
                            this,
                            requiresPermission,
                            galleryRequestCode
                    ).hasPermissions()
            ) {
                Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }.also {
                    startActivityForResult(it, RESULT_GALLERY_PICK)
                }
            }
        }
        binding.checkBtn.setOnClickListener {
            val user = viewModel.currentUser()
            user?.run {
                binding.apply {
                    name = etName.text.toString()
                    tagline = etBio.text.toString()
                    viewModel.userProfilePic.value?.let { url ->
                        profilePicUrl = url
                    }
                }
            }
        }
        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.userProfilePic.observe(this, Observer {
            Glide.with(binding.profilePic.context)
                    .load(it)
                    .placeholder(R.drawable.ic_profile_add_pic)
                    .into(binding.profilePic)
        })
        viewModel.username.observe(this, Observer {
            binding.etName.setText(it)
        })
        viewModel.userBio.observe(this, Observer {
            binding.etBio.setText(it)
        })
        viewModel.userEmail.observe(this, Observer {
            binding.etEmail.setText(it)
        })
        viewModel.status.observe(this, Observer {
            if (it == true) {
                showDialog()
            } else {
                onBackPressed()
            }
        })
    }

    private fun showDialog() {
//        TODO("Deprecated")
        val dialog = ProgressDialog(this)
        dialog.isIndeterminate = true
        dialog.setTitle("Saving Profile Please Wait")
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
            activityComponent.inject(this)

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RESULT_GALLERY_PICK -> {
                    try {
                        intent?.data?.let {
                            viewModel.onGalleryImageSelected(it)
                        } ?: showMessage(R.string.try_again)
                    } catch (e: FileNotFoundException) {
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
