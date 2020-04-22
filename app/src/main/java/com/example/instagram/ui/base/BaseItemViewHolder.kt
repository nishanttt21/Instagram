package com.example.instagram.ui.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.InstagramApp
import com.example.instagram.di.component.DaggerViewHolderComponent
import com.example.instagram.di.component.ViewHolderComponent
import com.example.instagram.di.module.ViewHolderModule
import javax.inject.Inject

abstract class BaseItemViewHolder<DB : ViewDataBinding, T : Any, VM : BaseItemViewModel<T>>(
    protected val binding: DB
) :
    RecyclerView.ViewHolder(binding.root), LifecycleOwner {
    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var lifecycleRegistry: LifecycleRegistry
    override fun getLifecycle(): Lifecycle = lifecycleRegistry
    open fun bind(data: T) {
        viewModel.updateData(data)
    }

    protected abstract fun setupView()

    protected abstract fun injectDependencies(viewHolderComponent: ViewHolderComponent)

    init {
        onCreate()
    }

    protected fun onCreate() {
        injectDependencies(buildViewHolderComponent())
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        setupObservers()
        setupView()
    }

    fun onStart() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun onStop() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    fun onDestroy() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    private fun buildViewHolderComponent(): ViewHolderComponent =
        DaggerViewHolderComponent.builder()
            .applicationComponent((itemView.context.applicationContext as InstagramApp).applicationComponent)
            .viewHolderModule(ViewHolderModule(this))
            .build()

    fun showMessage(message: String):
            Unit = Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()

    private fun showMessage(@StringRes resId: Int) = showMessage(itemView.context.getString(resId))

}