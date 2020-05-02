package com.example.instagram.ui.profile.editprofile

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
import timber.log.Timber
import java.io.FileNotFoundException

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {

    companion object {
        private const val RESULT_GALLERY_PICK = 1001
    }

    override fun provideLayoutId(): Int = R.layout.activity_edit_profile

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.tvChangeProfile.setOnClickListener {
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }.also {
                startActivityForResult(it, RESULT_GALLERY_PICK)
            }
        }
        binding.checkBtn.setOnClickListener {
            val user = viewModel.currentUser()
            user?.run {
                binding.apply {
                    name = etName.text.toString()
                    email = etEmail.text.toString()
                    tagline = etBio.text.toString()
                    viewModel.profilePic.value?.let { url ->
                        profilePicUrl = url
                    }
                }

                viewModel.updateData(this)
            }
        }
        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchMyInfo()

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.profilePic.observe(this, Observer {
            Glide.with(binding.profilePic.context)
                .load(it)
                .placeholder(R.drawable.ic_profile_add_pic)
                .into(binding.profilePic)
        })
        viewModel.myInfo.observe(this, Observer {
            it?.let {
                binding.apply {
                    etName.setText(it.name)
                    etEmail.setText(it.email)
                    etBio.setText(it.tagline)
                    it.profilePicUrl?.let {
                        Glide.with(profilePic.context)
                            .load(it)
                            .placeholder(R.drawable.ic_profile_add_pic)
                            .into(profilePic)

                    }
                }
            }
        })
        viewModel.status.observe(this, Observer {
            if (it == true) {
                showDialog()
            } else {
                finish()
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
                            this.contentResolver?.openInputStream(it)?.run {
                                viewModel.onGalleryImageSelected(this)
                            }
                        } ?: showMessage(R.string.try_again)
                    } catch (e: FileNotFoundException) {
                        Timber.e(e)
                        showMessage(R.string.try_again)
                    }
                }
            }
        }
    }
}