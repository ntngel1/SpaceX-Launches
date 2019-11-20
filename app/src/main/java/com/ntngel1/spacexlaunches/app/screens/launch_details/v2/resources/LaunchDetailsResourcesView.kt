package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.resources

import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchDetailsResourcesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setResourceLinks(links: List<ResourceLinkEntity>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsStubVisible(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsLoading(isLoading: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openUrl(url: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsLoadingError(isLoadingError: Boolean)
}