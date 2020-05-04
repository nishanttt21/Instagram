package com.example.instagram.utils.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by @author Deepak Dawade on 5/4/20.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 */
@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, url: String?) {
    url?.let { Glide.with(view.context).load(it).into(view) }
}

@BindingAdapter("app:loadImageFresco")
fun loadImageFresco(view: ImageView, url: String?) {
    url?.let { view.setImageURI(url.toUri()) }
}

@BindingAdapter("app:onCloseIconClick")
fun onCloseIconClick(view: Chip, body: () -> Unit) {
    view.setOnCloseIconClickListener {
        body()
    }
}

@BindingAdapter("app:handleMultipleClicks")
fun handleMultipleClicksListener(view: View, body: () -> Unit) {
    view.setOnClickListener {
        view.isEnabled = false
        body()
        Handler().postDelayed({
            if (view.parent != null) {
                view.isEnabled = true
            }
        }, 700)
    }
}

@IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
@Retention(AnnotationRetention.SOURCE)
annotation class Visibility

@BindingAdapter("app:animateVisibility")
fun animateVisibility(view: View, @Visibility visibility: Int) {
    view.apply {
        val shortAnimationDuration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        when (visibility) {
            View.VISIBLE -> {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = 0f
                view.visibility = View.VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null)
            }
            View.INVISIBLE, View.GONE -> {
                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't
                // participate in layout passes, etc.)
                animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            view.visibility = visibility
                        }
                    })
            }
        }
    }
}

@BindingAdapter("app:textInputLayoutValidation")
fun textInputLayoutValidation(view: TextInputLayout, resourceLiveData: LiveData<_Resource<Int>>) {
    resourceLiveData.value?.run {
        when (status) {
            ResourceState.Error -> view.error = data?.let { view.context.getString(it) }
            else -> view.isErrorEnabled = false
        }
    }
}

@BindingAdapter("app:value")
fun setValue(view: NumberPicker, newValue: Int) {
    if (view.value != newValue) {
        view.value = newValue
    }
}

@InverseBindingAdapter(attribute = "app:value")
fun getValue(view: NumberPicker): Int {
    return view.value
}


@BindingAdapter(value = ["valueAttrChanged"])
fun setListener(view: NumberPicker, listener: InverseBindingListener?) {
    if (listener != null) {
        view.setOnValueChangedListener { _, _, _ ->
            listener.onChange()
        }
    }
}

@BindingAdapter("srcVector")
fun setSrcVector(view: AppCompatImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}
