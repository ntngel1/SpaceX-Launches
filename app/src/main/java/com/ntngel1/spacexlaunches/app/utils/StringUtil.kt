package com.ntngel1.spacexlaunches.app.utils

fun isNullOrBlank(vararg strings: String?) = strings.all { it.isNullOrBlank() }