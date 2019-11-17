package com.ntngel1.spacexlaunches.app.utils

import android.view.View
import android.widget.TextView

fun TextView.setTextOrGone(text: String?) {
    if (text.isNullOrBlank()) {
        visibility = View.GONE
    } else {
        this.text = text
        visibility = View.VISIBLE
    }
}