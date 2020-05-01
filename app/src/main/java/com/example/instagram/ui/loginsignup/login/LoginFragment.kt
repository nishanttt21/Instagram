package com.example.instagram.ui.loginsignup.login

import android.content.Intent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.instagram.R
import com.example.instagram.databinding.FragmentLoginBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import com.example.instagram.ui.loginsignup.login.signup.login.LoginViewModel
import com.example.instagram.ui.main.MainActivity
import com.example.instagram.utils.common.Status

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun provideLayoutId(): Int = R.layout.fragment_login

    override fun setupView() {
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
            findNavController().navigate(R.id.signUpFragment)
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.launchMain.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
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
        private const val TAG = "LoginFragment"
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

}
