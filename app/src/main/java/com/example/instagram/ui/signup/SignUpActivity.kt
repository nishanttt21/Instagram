package com.example.instagram.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.databinding.ActivitySignupBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.login.LoginActivity
import com.example.instagram.ui.main.MainActivity
import com.example.instagram.utils.common.Status

class SignUpActivity : BaseActivity<ActivitySignupBinding, SignUpViewModel>() {
    override fun provideLayoutId(): Int = R.layout.activity_signup

    override fun setupView(savedInstanceState: Bundle?) {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChange(text.toString())
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChange(text.toString())
        }
        binding.etName.doOnTextChanged { text, _, _, _ ->
            viewModel.onNameChange(text.toString())
        }

        binding.btnSignUp.setOnClickListener {
            viewModel.doSignUp()
        }
        binding.loginWithEmailBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupObservers() {
        super.setupObservers()
        viewModel.launchMain.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })
        viewModel.emailField.observe(this, Observer {
            if (it != binding.etEmail.text.toString())
                binding.etEmail.setText(it)
        })
        viewModel.passwordField.observe(this, Observer {
            if (it != binding.etPassword.text.toString())
                binding.etPassword.setText(it)
        })
        viewModel.nameField.observe(this, Observer {
            if (it != binding.etName.text.toString())
                binding.etName.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.emailLayout.error = it.data?.run { getString(this) }
                else -> binding.emailLayout.isErrorEnabled = false
            }
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.passwordLayout.error = it.data?.run { getString(this) }
                else -> binding.passwordLayout.isErrorEnabled = false
            }
        })
        viewModel.nameValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.nameLayout.error = it.data?.run { getString(this) }
                else -> binding.nameLayout.isErrorEnabled = false
            }
        })
        viewModel.signingIn.observe(this, Observer {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}