package com.example.instagram.ui.loginsignup.signup

import android.content.Intent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSignupBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.loginsignup.login.LoginFragment
import com.example.instagram.ui.loginsignup.login.signup.SignUpViewModel
import com.example.instagram.utils.common.Status

class SignUpFragment : BaseFragment<FragmentSignupBinding, SignUpViewModel>() {
    override fun provideLayoutId(): Int = R.layout.fragment_signup

    override fun setupView() {
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
            startActivity(Intent(requireContext(), LoginFragment::class.java))
        }
    }


    override fun setupObservers() {
        super.setupObservers()
        viewModel.launchMain.observe(this, Observer {
            it.getIfNotHandled()?.run {
                findNavController().navigateUp()

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
        private const val TAG = "SignUpFragment"
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)
}