package com.example.instagram.ui.dummy

import android.os.Bundle
import com.example.instagram.R
import com.example.instagram.databinding.ActivityDummyBinding
import com.example.instagram.di.component.ActivityComponent
import com.example.instagram.ui.base.BaseActivity
import com.example.instagram.ui.dummies.DummiesFragment

class DummyActivity : BaseActivity<ActivityDummyBinding, DummyViewModel>() {

    companion object {
        private const val TAG = "DummyActivity"
    }

    override fun provideLayoutId(): Int = R.layout.activity_dummy

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        addDummiesFragment()
    }

    private fun addDummiesFragment() {
        supportFragmentManager.findFragmentByTag(DummiesFragment.TAG) ?: supportFragmentManager
            .beginTransaction()
            .add(binding.containerFragment.id, DummiesFragment.newInstance(), DummiesFragment.TAG)
            .commitAllowingStateLoss()
    }
}