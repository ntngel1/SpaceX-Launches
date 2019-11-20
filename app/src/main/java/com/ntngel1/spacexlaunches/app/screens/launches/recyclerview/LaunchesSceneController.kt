package com.ntngel1.spacexlaunches.app.screens.launches.recyclerview

import com.ntngel1.spacexlaunches.app.common.recyclerview.progress_bar.ProgressBarViewModel
import com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.launch.LaunchViewModel
import com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.year.YearViewModel
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.SceneController
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModel
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LaunchesSceneController @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter
) : SceneController() {

    var launches = emptyList<LaunchEntity>()
    var isProgressBarVisible = false

    var onLaunchClicked: ((launch: LaunchEntity) -> Unit)? = null

    override fun buildViewModels(): List<ViewModel> {
        val viewModels = ArrayList<ViewModel>(launches.size)

        var lastYear = Integer.MAX_VALUE
        launches.forEach { launch ->

            // Разбивка по годам
            if (launch.launchDate.year < lastYear) {
                lastYear = launch.launchDate.year

                YearViewModel(id = "year$lastYear", year = lastYear)
                    .let(viewModels::add)
            }

            buildLaunch(launch).let(viewModels::add)
        }

        if (isProgressBarVisible) {
            ProgressBarViewModel().let(viewModels::add)
        }

        return viewModels
    }

    private fun buildLaunch(launch: LaunchEntity) =
        LaunchViewModel(
            id = "launch${launch.flightNumber}",
            title = launch.missionName,
            launchDate = launch.launchDate.format(dateTimeFormatter),
            imageUrl = launch.links.missionPatchSmall,
            onClicked = { onLaunchClicked?.invoke(launch) }
        )
}