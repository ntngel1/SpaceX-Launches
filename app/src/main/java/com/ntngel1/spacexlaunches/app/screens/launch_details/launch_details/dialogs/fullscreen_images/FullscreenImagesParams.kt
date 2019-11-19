package com.ntngel1.spacexlaunches.app.screens.launch_details.launch_details.dialogs.fullscreen_images

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FullscreenImagesParams(
    val images: List<Image>,
    val offset: Int = 0
) : Parcelable

@Parcelize
data class Image(
    val url: String,
    val title: String? = null
) : Parcelable