package com.example.instagram.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.main.MainActivity
import com.example.instagram.ui.signup.SignUpActivity
import com.example.instagram.utils.common.Status

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_login

    override fun injectDependencies(activityComponent: ActivityComponent):
            Unit = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChange(text.toString())
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChange(text.toString())
        }
        binding.btnLogin.setOnClickListener {
            viewModel.doLogin()
        }
        binding.signUpWithEmailBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

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
        viewModel.loggingIn.observe(this, Observer {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    companion object {
        private const val TAG = "LoginActivity"
    }

}
