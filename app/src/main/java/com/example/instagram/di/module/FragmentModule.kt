package com.example.instagram.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.instagram.di.ActivityContext
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment: Fragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!
}