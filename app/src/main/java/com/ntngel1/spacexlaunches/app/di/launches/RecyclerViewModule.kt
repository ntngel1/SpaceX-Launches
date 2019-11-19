package com.ntngel1.spacexlaunches.app.di.launches

import com.ntngel1.spacexlaunches.app.ui.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.ui.recyclerview.progress_bar.ProgressBarViewBinder
import com.ntngel1.spacexlaunches.app.ui.screens.launches.LaunchesPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launches.recyclerview.launch.LaunchViewBinder
import com.ntngel1.spacexlaunches.app.ui.screens.launches.recyclerview.year.YearViewBinder
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModelAdapter
import com.ntngel1.spacexlaunches.app.utils.dp
import dagger.Module
import dagger.Provides

@Module
class RecyclerViewModule(
    private val onLoadMoreLaunchesListener: () -> Unit
) {

    @Provides
    @LaunchesScope
    fun provideViewModelAdapter() = ViewModelAdapter().apply {
        registerViewBinder(LaunchViewBinder())
        registerViewBinder(ProgressBarViewBinder())
        registerViewBinder(YearViewBinder())
    }

    @Provides
    @LaunchesScope
    fun provideMarginItemDecoration() = ListMarginItemDecoration(
        betweenElementsMargin = 8.dp,
        startMargin = 8.dp,
        leftMargin = 8.dp,
        rightMargin = 8.dp
    )

    @Provides
    @LaunchesScope
    fun providePaginationScrollListener() = PaginationScrollListener(
        LaunchesPresenter.LAUNCHES_LIMIT,
        onLoadMoreLaunchesListener
    )

}