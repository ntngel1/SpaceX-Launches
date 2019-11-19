package com.ntngel1.spacexlaunches.app.di.launch_details.v1

import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.LaunchDetailsV1Fragment
import dagger.Subcomponent

@LaunchDetailsV1Scope
@Subcomponent
interface LaunchDetailsV1Component {

    val presenter: LaunchDetailsPresenter

    fun inject(target: LaunchDetailsV1Fragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): LaunchDetailsV1Component
    }
}