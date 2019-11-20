package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.images

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchDetailsImagesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setImages(imageUrls: List<String>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsStubVisible(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsLoading(isLoading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsLoadingError(isLoadingError: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showImagesFullscreen(imageUrls: List<String>, offset: Int = 0)
}