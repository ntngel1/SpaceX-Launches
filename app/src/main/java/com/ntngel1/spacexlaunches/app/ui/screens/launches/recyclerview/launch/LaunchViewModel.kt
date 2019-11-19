package com.ntngel1.spacexlaunches.app.ui.screens.launches.recyclerview.launch

import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModel

data class LaunchViewModel(
    override val id: String,
    val title: String,
    val launchDate: String,
    val imageUrl: String?,
    val onClicked: () -> Unit
) : ViewModel