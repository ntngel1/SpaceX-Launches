package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_resources

import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LaunchDetailsResourcesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsLoading(isLoading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setResourceLinks(links: List<ResourceLinkEntity>)
}