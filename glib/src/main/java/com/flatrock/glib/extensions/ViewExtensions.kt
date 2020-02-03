package com.flatrock.glib.extensions

import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.flatrock.glib.listeners.UPageSelectedListener

inline fun ViewPager.setOnPageSelectedListener(
    func: UPageSelectedListener.() -> Unit) {
    val listener = UPageSelectedListener()
    listener.func()
    addOnPageChangeListener(listener)
}

fun ImageView.loadImage(imageUrl: String?, placeHolder: Int = -1, errorPlaceHolder: Int = -1) {
    var glideOperation = Glide.with(context).load(imageUrl)
    if (placeHolder != -1)
        glideOperation = glideOperation.placeholder(placeHolder)
    if (errorPlaceHolder != -1)
        glideOperation = glideOperation.error(errorPlaceHolder)
    glideOperation.into(this)
}