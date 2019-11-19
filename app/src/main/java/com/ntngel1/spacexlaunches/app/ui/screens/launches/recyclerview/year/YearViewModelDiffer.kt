package com.ntngel1.spacexlaunches.app.ui.screens.launches.recyclerview.year

import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModelDiffer

class YearViewModelDiffer : ViewModelDiffer<YearViewModel, Nothing>() {

    override fun areContentsTheSame(old: YearViewModel, new: YearViewModel) =
        old.year == new.year
}