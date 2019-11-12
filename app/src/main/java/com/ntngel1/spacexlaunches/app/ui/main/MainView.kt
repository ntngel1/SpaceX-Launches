package com.ntngel1.spacexlaunches.app.ui.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openLaunchesScreen()
}