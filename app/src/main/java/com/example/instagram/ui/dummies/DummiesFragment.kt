package com.example.instagram.ui.dummies

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentDummiesBinding
import com.example.instagram.di.component.FragmentComponent
import com.example.instagram.ui.base.BaseFragment
import javax.inject.Inject

class DummiesFragment : BaseFragment<FragmentDummiesBinding, DummiesViewModel>() {

    companion object {

        internal const val TAG = "DummiesFragment"

        fun newInstance(): DummiesFragment {
            val args = Bundle()
            val fragment = DummiesFragment()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var dummiesAdapter: DummiesAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_dummies

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        viewModel.getDummies().observe(this, Observer {
            it?.run { dummiesAdapter.appendData(this) }
        })
    }

    override fun setupView() {
        binding.rvDummy.apply {
            layoutManager = GridLayoutManager(requireContext(),3).apply {
                spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        val count = dummiesAdapter.itemCount % 3
                        return if(position == dummiesAdapter.itemCount-1) count else 1
                    }
                }
            }
            adapter = dummiesAdapter
        }
    }

}