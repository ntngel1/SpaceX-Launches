package com.ntngel1.spacexlaunches.app.utils

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar

inline fun Toolbar.setupToolbar(
    title: String? = null,
    @ColorInt titleColor: Int? = null,
    @DrawableRes navigationIconId: Int,
    crossinline onNavigateBack: () -> Unit
) {
    title?.let(::setTitle)
    titleColor?.let(::setTitleTextColor)
    setNavigationIcon(navigationIconId)
    setNavigationOnClickListener { onNavigateBack.invoke() }
}