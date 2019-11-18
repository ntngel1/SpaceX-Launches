package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_images

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchDetailsImagesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setImages(imageUrls: List<String>)
}