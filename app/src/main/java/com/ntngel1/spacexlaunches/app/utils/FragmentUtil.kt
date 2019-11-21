package com.ntngel1.spacexlaunches.app.utils

import android.view.WindowManager
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T> Fragment.argument(name: String? = null): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T> {
        override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return thisRef.arguments?.get(name ?: property.name) as T
        }
    }

fun Fragment.setTranslucentStatusBar(isTranslucent: Boolean) = activity?.let { activity ->
    if (isTranslucent) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    } else {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}