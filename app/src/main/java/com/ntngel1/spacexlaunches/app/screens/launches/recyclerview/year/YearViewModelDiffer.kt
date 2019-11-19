package com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.year

import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModelDiffer

class YearViewModelDiffer : ViewModelDiffer<YearViewModel, Nothing>() {

    override fun areContentsTheSame(old: YearViewModel, new: YearViewModel) =
        old.year == new.year
}