package com.example.instagram.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.dummy.DummyActivity
import com.example.instagram.utils.common.Status
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun provideLayoutId(): Int = R.layout.activity_login

    override fun injectDependencies(activityComponent: ActivityComponent):
            Unit = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        et_email.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChange(text.toString())
        }
        et_password.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChange(text.toString())
        }
        btn_login.setOnClickListener {
            viewModel.doLogin()
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.launchDummy.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, DummyActivity::class.java))
                finish()
            }
        })
//        viewModel.emailField.observe(this, Observer {
//            if (it != et_email.text.toString())
//                et_email.setText(it)
//        })
//        viewModel.passwordField.observe(this, Observer {
//            if (it != et_password.text.toString())
//                et_password.setText(it)
//        })

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
        viewModel.loggingIn.observe(this, Observer {
            pb_loading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

}
