package com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.month

import android.view.View
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewBinder
import kotlinx.android.synthetic.main.item_month.view.*
import kotlin.reflect.KClass

class MonthViewBinder : ViewBinder<MonthViewModel, MonthViewModelDiffer, Nothing, Nothing>() {

    override val layoutId: Int
        get() = R.layout.item_month

    override val viewModelClass: KClass<MonthViewModel>
        get() = MonthViewModel::class

    override val viewModelDiffer: MonthViewModelDiffer
        get() = MonthViewModelDiffer()

    override fun bind(viewModel: MonthViewModel, itemView: View, hasViewState: Boolean) {
        super.bind(viewModel, itemView, hasViewState)
        itemView.text_month.text = viewModel.month
    }
}