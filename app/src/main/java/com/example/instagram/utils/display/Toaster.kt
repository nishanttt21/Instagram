package com.example.instagram.utils.display

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.instagram.R

object Toaster {
    fun show(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.colorWhite), PorterDuff.Mode.SRC_IN
        )
        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
        toast.show()
    }
}