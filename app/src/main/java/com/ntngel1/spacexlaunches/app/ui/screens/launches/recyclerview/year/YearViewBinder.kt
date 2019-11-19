package com.ntngel1.spacexlaunches.app.ui.screens.launches.recyclerview.year

import android.view.View
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewBinder
import kotlinx.android.synthetic.main.item_year.view.*
import kotlin.reflect.KClass

class YearViewBinder : ViewBinder<YearViewModel, YearViewModelDiffer, Nothing, Nothing>() {

    override val layoutId: Int
        get() = R.layout.item_year

    override val viewModelClass: KClass<YearViewModel>
        get() = YearViewModel::class

    override val viewModelDiffer: YearViewModelDiffer
        get() = YearViewModelDiffer()

    override fun bind(viewModel: YearViewModel, itemView: View, hasViewState: Boolean) {
        super.bind(viewModel, itemView, hasViewState)
        with(itemView) {
            text_year.text = viewModel.year.toString()
        }
    }
}