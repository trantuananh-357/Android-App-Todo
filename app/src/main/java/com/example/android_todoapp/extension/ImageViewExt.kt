package com.example.android_todoapp.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.android_todoapp.App

fun ImageView.loadImage(url: String?, sizeValue : Int) {
    Glide.with(App.getInstance())
        .asBitmap()
        .load(url)
        .override(sizeValue)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                this@loadImage.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                /* no-op */
            }
        })
}

fun ImageView.loadImage(url: String?) {
    Glide.with(App.getInstance())
        .load(url)
        .into(this)
}

fun ImageView.loadImage(@DrawableRes rawRes: Int) {
    Glide.with(App.getInstance())
        .load(rawRes)
        .into(this)
}

fun ImageView.loadImage(url: String?, sizeValue: Int, onFail: () -> Unit) {
    Glide.with(App.getInstance())
        .load(url)
        .override(sizeValue)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                onFail()
                return false
            }
        })
        .into(this)
}