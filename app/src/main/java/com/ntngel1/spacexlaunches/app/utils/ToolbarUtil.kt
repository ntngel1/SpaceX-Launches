package com.ntngel1.spacexlaunches.app.utils

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar

inline fun Toolbar.setupToolbar(
    title: String? = null,
    @DrawableRes navigationIconId: Int,
    crossinline onNavigateBack: () -> Unit
) {
    title?.let(::setTitle)
    setNavigationIcon(navigationIconId)
    setNavigationOnClickListener { onNavigateBack.invoke() }
}