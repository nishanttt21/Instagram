package com.example.instagram.ui.dummies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagram.data.model.Dummy
import com.example.instagram.databinding.ItemViewDummiesBinding
import com.example.instagram.di.component.ViewHolderComponent
import com.example.instagram.ui.base.BaseItemViewHolder

class DummyItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<ItemViewDummiesBinding, Dummy, DummyItemViewModel>(
        ItemViewDummiesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.name.observe(this, Observer {
            binding.tvHeadLineDummy.text = it
        })

        viewModel.url.observe(this, Observer {
            Glide.with(itemView.context).load(it).into(binding.ivDummy)
        })
    }

    override fun setupView() {
        binding.root.setOnClickListener {
            viewModel.onItemClick(adapterPosition)
        }
    }
}