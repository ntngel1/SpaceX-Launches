package com.ntngel1.spacexlaunches.app.di.launch_details.v2

import com.ntngel1.spacexlaunches.app.di.launch_details.v1.LaunchDetailsV1Scope
import com.ntngel1.spacexlaunches.app.ui.recyclerview.CarouselMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v2.LaunchDetailsV2Fragment
import dagger.Subcomponent

@LaunchDetailsV1Scope
@Subcomponent(modules = [RecyclerViewModule::class])
interface LaunchDetailsV2Component {

    val presenter: LaunchDetailsPresenter
    val carouselMarginItemDecoration: CarouselMarginItemDecoration

    fun inject(target: LaunchDetailsV2Fragment)

    @Subcomponent.Builder
    interface Builder {
        fun recyclerViewModule(module: RecyclerViewModule): Builder
        fun build(): LaunchDetailsV2Component
    }
}