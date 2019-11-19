package com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.month

import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModelDiffer

class MonthViewModelDiffer : ViewModelDiffer<MonthViewModel, Nothing>() {

    override fun areContentsTheSame(old: MonthViewModel, new: MonthViewModel) =
        old.month == new.month
}