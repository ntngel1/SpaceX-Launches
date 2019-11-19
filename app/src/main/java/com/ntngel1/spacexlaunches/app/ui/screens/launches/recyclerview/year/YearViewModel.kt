package com.ntngel1.spacexlaunches.app.ui.screens.launches.recyclerview.year

import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModel

data class YearViewModel(
    override val id: String,
    val year: Int
) : ViewModel