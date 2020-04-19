package com.example.instagram.ui.post

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.instagram.R
import com.example.instagram.di.component.ViewHolderComponent
import com.example.instagram.ui.base.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_view_post.view.*

class PostViewHolder(parent: ViewGroup) : BaseItemViewHolder<Post, PostViewModel>(
    R.layout.item_view_post, parent
) {
    override fun setupView(view: View) {
        view.setOnClickListener {
            showMessage("$adapterPosition clicked")
        }
    }

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.data.observe(this, Observer {
            itemView.tv_message.text = it.text
        })
    }
}