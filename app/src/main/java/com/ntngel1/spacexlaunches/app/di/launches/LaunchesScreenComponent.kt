package com.ntngel1.spacexlaunches.app.di.launches

import com.ntngel1.spacexlaunches.app.ui.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.LaunchesFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.LaunchesPresenter
import dagger.Subcomponent

@LaunchesScreenScope
@Subcomponent(modules = [RecyclerViewLaunchesScreenModule::class])
interface LaunchesScreenComponent {

    val presenter: LaunchesPresenter
    val marginItemDecoration: ListMarginItemDecoration
    val paginationScrollListener: PaginationScrollListener

    fun inject(target: LaunchesFragment)

    @Subcomponent.Builder
    interface Builder {
        fun recyclerViewModule(module: RecyclerViewLaunchesScreenModule): Builder
        fun build(): LaunchesScreenComponent
    }
}