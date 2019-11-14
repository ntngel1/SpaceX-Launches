package com.ntngel1.spacexlaunches.app.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.str(@StringRes resId: Int, vararg formatArgs: Any): String =
    if (formatArgs.isNotEmpty()) getString(resId, *formatArgs) else getString(resId)

fun Fragment.str(@StringRes resId: Int, vararg formatArgs: Any): String =
    context!!.str(resId, *formatArgs)