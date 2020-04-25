package com.example.instagram.ui.profile.editprofile

import android.app.ProgressDialog
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.ActivityEditProfileBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {
    override fun provideLayoutId(): Int = R.layout.activity_edit_profile

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.checkBtn.setOnClickListener {
            val user = viewModel.currentUser.value
            user?.run {
                binding.apply {
                    user.name = etName.text.toString()
                    user.email = etEmail.text.toString()
                    user.tagline = etBio.text.toString()
                }
                viewModel.updateData(user)
            }
        }
        binding.closeBtn.setOnClickListener { onBackPressed() }

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.currentUser.observe(this, Observer {
            it?.run {
                binding.apply {
                    etName.setText(name)
                    etEmail.setText(email)
                    etBio.setText(tagline)
                    profilePicUrl?.let {
                        Glide.with(profilePic)
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
}