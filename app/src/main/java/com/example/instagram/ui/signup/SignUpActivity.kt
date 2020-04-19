package com.example.instagram.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.dummy.DummyActivity
import com.example.instagram.ui.login.LoginActivity
import com.example.instagram.utils.common.Status
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : BaseActivity<SignUpViewModel>() {
    companion object {
        private val TAG = SignUpActivity::class.simpleName
    }

    override fun provideLayoutId(): Int = R.layout.activity_signup

    override fun setupView(savedInstanceState: Bundle?) {
        et_email.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChange(text.toString())
        }
        et_password.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChange(text.toString())
        }
        et_name.doOnTextChanged { text, _, _, _ ->
            viewModel.onNameChange(text.toString())
        }

        btn_sign_up.setOnClickListener {
            viewModel.doSignUp()
        }
        loginWithEmailBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupObservers() {
        super.setupObservers()
        viewModel.launchDummy.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, DummyActivity::class.java))
                finish()
            }
        })
        viewModel.emailField.observe(this, Observer {
            if (it != et_email.text.toString())
                et_email.setText(it)
        })
        viewModel.passwordField.observe(this, Observer {
            if (it != et_password.text.toString())
                et_password.setText(it)
        })
        viewModel.nameField.observe(this, Observer {
            if (it != et_name.text.toString())
                et_name.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> email_layout.error = it.data?.run { getString(this) }
                else -> email_layout.isErrorEnabled = false
            }
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> password_layout.error = it.data?.run { getString(this) }
                else -> password_layout.isErrorEnabled = false
            }
        })
        viewModel.nameValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> name_layout.error = it.data?.run { getString(this) }
                else -> name_layout.isErrorEnabled = false
            }
        })
        viewModel.signingIn.observe(this, Observer {
            pb_loading.visibility = if (it) View.VISIBLE else View.GONE
        })


    }
}