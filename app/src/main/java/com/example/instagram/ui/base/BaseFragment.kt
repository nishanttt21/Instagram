package com.example.instagram.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.instagram.InstagramApp
import com.example.instagram.di.component.DaggerFragmentComponent
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.di.module.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    @Inject
    lateinit var viewModel: VM
    protected lateinit var binding: DB

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView()

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, provideLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.let { showMessage(it) }
        })
        viewModel.messageStringId.observe(this, Observer {
            it.data?.let { showMessage(it) }
        })
    }

    private fun buildFragmentComponent(): FragmentComponent =
        DaggerFragmentComponent.builder()
            .applicationComponent((context?.applicationContext as InstagramApp).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()

    fun showMessage(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))
}
