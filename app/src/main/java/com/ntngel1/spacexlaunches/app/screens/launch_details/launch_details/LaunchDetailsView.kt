package com.ntngel1.spacexlaunches.app.screens.launch_details.launch_details

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchDetailsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setProgressBarIsVisible(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLaunchDetails(launch: LaunchEntity)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showImagesFullscreen(imageUrls: List<String>, offset: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateBackWithLoadingError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showImageWithTitle(imageUrl: String, title: String)
}