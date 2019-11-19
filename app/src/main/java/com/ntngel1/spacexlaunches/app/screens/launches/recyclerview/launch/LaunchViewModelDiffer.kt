package com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.launch

import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModelDiffer

class LaunchViewModelDiffer : ViewModelDiffer<LaunchViewModel, Nothing>() {

    override fun areContentsTheSame(old: LaunchViewModel, new: LaunchViewModel) =
        old.imageUrl == new.imageUrl &&
                old.launchDate == new.launchDate &&
                old.title == new.title
}