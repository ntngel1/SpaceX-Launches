package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import org.threeten.bp.ZonedDateTime

interface LaunchDetailsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setProgressBarIsVisible(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLaunchDetails(launch: LaunchEntity)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showImagesFullscreen(images: List<String>, offset: Int)
}