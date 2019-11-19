package com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.launch

import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModel

data class LaunchViewModel(
    override val id: String,
    val title: String,
    val launchDate: String,
    val imageUrl: String?,
    val onClicked: () -> Unit
) : ViewModel