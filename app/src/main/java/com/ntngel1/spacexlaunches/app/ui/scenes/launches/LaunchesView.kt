package com.ntngel1.spacexlaunches.app.ui.scenes.launches

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setLaunches(launches: List<LaunchEntity>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsProgressBarVisible(isVisible: Boolean)
}