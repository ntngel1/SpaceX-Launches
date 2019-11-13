package com.ntngel1.spacexlaunches.app.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ntngel1.spacexlaunches.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.color.colorGray)
        .error(R.drawable.placeholder_no_image_available)
        .into(this)
}