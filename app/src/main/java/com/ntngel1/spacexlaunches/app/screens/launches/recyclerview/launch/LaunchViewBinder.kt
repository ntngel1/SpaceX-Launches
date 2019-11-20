package com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.launch

import android.view.View
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewBinder
import com.ntngel1.spacexlaunches.app.utils.loadImage
import kotlinx.android.synthetic.main.item_launch.view.*
import kotlin.reflect.KClass

class LaunchViewBinder : ViewBinder<LaunchViewModel, LaunchViewModelDiffer, Nothing, Nothing>() {

    override val layoutId: Int
        get() = R.layout.item_launch

    override val viewModelClass: KClass<LaunchViewModel>
        get() = LaunchViewModel::class

    override val viewModelDiffer: LaunchViewModelDiffer
        get() = LaunchViewModelDiffer()

    override fun bind(viewModel: LaunchViewModel, itemView: View, hasViewState: Boolean) {
        super.bind(viewModel, itemView, hasViewState)
        with(itemView) {
            image_launch_patch.loadImage(viewModel.imageUrl)
            text_launch_title.text = viewModel.title
            text_launch_launch_date.text = viewModel.launchDate

            setOnClickListener { viewModel.onClicked.invoke() }
        }
    }
}