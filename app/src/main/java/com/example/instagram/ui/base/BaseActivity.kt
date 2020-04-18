package com.example.instagram.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instagram.InstagramApp
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.di.component.DaggerActivityComponent
import com.example.instagram.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    @Inject
    lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        viewModel.onCreate()
        setupObservers()
        setupView(savedInstanceState)
    }

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            showMessage(it)
        })
        viewModel.messageStringId.observe(this, Observer {
            showMessage(it)
        })
    }

    private fun buildActivityComponent(): ActivityComponent =
        DaggerActivityComponent.builder()
            .applicationComponent((application as InstagramApp).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    fun showMessage(message: String) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)
}
