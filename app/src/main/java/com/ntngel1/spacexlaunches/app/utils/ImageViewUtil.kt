package com.ntngel1.spacexlaunches.app.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ntngel1.spacexlaunches.R

fun ImageView.loadImage(url: String?, @DrawableRes placeholder: Int? = R.color.colorGray) {
    Glide.with(this)
        .load(url)
        .also {
            placeholder?.let(it::placeholder)
        }
        .error(R.drawable.placeholder_no_image_available)
        .into(this)
}

fun ImageView.loadImageWithProgressBar(url: String?, progressBar: View) {
    progressBar.visibleOrGone(true)

    Glide.with(this)
        .load(url)
        .error(R.drawable.placeholder_no_image_available)
        .listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibleOrGone(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibleOrGone(false)
                return false
            }

        })
        .into(this)
}

fun ImageView.loadImageOrGone(url: String?) {
    Glide.with(this)
        .load(url)
        .listener(object: RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                visibleOrGone(false)
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                visibleOrGone(true)
                return false
            }
        })
        .into(this)
}