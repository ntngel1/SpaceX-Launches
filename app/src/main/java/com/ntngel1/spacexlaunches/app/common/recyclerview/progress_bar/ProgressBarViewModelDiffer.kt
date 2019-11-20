package com.ntngel1.spacexlaunches.app.common.recyclerview.progress_bar

import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModelDiffer

class ProgressBarViewModelDiffer : ViewModelDiffer<ProgressBarViewModel, Nothing>() {

    override fun areContentsTheSame(
        old: ProgressBarViewModel,
        new: ProgressBarViewModel
    ) = true
}