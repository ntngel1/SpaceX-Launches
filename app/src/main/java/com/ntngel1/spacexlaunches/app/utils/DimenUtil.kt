package com.ntngel1.spacexlaunches.app.utils

import android.content.res.Resources

/**
 * Конвертация dp в px
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()