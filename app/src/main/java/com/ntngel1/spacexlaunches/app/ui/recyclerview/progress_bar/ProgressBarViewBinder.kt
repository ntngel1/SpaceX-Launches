package com.ntngel1.spacexlaunches.app.ui.recyclerview.progress_bar

import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewBinder
import kotlin.reflect.KClass

class ProgressBarViewBinder :
    ViewBinder<ProgressBarViewModel, ProgressBarViewModelDiffer, Nothing, Nothing>() {

    override val layoutId: Int
        get() = R.layout.item_progress_bar

    override val viewModelClass: KClass<ProgressBarViewModel>
        get() = ProgressBarViewModel::class

    override val viewModelDiffer: ProgressBarViewModelDiffer
        get() = ProgressBarViewModelDiffer()
}