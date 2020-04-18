package com.example.instagram.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

class ViewModelProviderFactory<T : ViewModel>(
    private val Kclass: KClass<T>,
    private val creator: () -> T
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Kclass.java)) return creator() as T
        throw IllegalAccessException("Unknown Class Name")
    }
}