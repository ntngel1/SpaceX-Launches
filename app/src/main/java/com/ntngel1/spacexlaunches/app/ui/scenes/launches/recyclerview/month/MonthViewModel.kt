package com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.month

import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModel

data class MonthViewModel(
    override val id: String,
    val month: String
) : ViewModel