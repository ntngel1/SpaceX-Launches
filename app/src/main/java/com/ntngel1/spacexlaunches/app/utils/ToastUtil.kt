package com.ntngel1.spacexlaunches.app.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(@StringRes stringId: Int, vararg formatArgs: Any) {
    Toast.makeText(this, str(stringId, *formatArgs), Toast.LENGTH_SHORT)
        .show()
}