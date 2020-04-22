package com.example.instagram.di.module

import androidx.lifecycle.LifecycleRegistry
import com.example.instagram.di.ViewHolderScope
import com.example.instagram.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides


@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *, *>) {

    @ViewHolderScope
    @Provides
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)

}