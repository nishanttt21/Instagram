package com.example.instagram.di.component

import com.example.instagram.di.ViewHolderScope
import com.example.instagram.di.module.ViewHolderModule
import com.example.instagram.ui.dummies.DummyItemViewHolder
import com.example.instagram.ui.post.PostViewHolder
import dagger.Component

@ViewHolderScope
@Component(dependencies = [ApplicationComponent::class], modules = [ViewHolderModule::class])
interface ViewHolderComponent {
    fun inject(viewHolder: PostViewHolder)
    fun inject(viewHolder: DummyItemViewHolder)
}
