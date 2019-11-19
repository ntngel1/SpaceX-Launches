package com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.year

import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModel

data class YearViewModel(
    override val id: String,
    val year: Int
) : ViewModel