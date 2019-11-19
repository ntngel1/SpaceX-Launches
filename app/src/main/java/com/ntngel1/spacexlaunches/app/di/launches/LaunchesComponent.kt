package com.ntngel1.spacexlaunches.app.di.launches

import com.ntngel1.spacexlaunches.app.ui.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.ui.screens.launches.LaunchesFragment
import com.ntngel1.spacexlaunches.app.ui.screens.launches.LaunchesPresenter
import dagger.Subcomponent

@LaunchesScope
@Subcomponent(modules = [RecyclerViewModule::class])
interface LaunchesComponent {

    val presenter: LaunchesPresenter
    val marginItemDecoration: ListMarginItemDecoration
    val paginationScrollListener: PaginationScrollListener

    fun inject(target: LaunchesFragment)

    @Subcomponent.Builder
    interface Builder {
        fun recyclerViewModule(module: RecyclerViewModule): Builder
        fun build(): LaunchesComponent
    }
}