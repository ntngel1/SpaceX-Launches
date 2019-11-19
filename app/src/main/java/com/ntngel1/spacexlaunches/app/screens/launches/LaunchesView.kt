package com.ntngel1.spacexlaunches.app.screens.launches

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchesView : MvpView {

    // RecyclerView

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setLaunches(launches: List<LaunchEntity>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsProgressBarVisible(isVisible: Boolean)

    // Fragment

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsRefreshing(isRefreshing: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openLaunchDetailsScene(flightNumber: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoadingError()
}