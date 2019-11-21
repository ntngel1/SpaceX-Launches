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

        launches.groupBy { it.launchDate.year }
            .toList()
            .forEach { (year, launches) ->
                year.toYearViewModel()
                    .let(viewModels::add)

                launches.map { it.toLaunchViewModel() }
                    .let(viewModels::addAll)
            }

        if (isProgressBarVisible) {
            ProgressBarViewModel().let(viewModels::add)
        }

        return viewModels
    }

    private fun Int.toYearViewModel() = YearViewModel(id = "year$this", year = this)

    private fun LaunchEntity.toLaunchViewModel() = LaunchViewModel(
        id = "launch${flightNumber}",
        title = missionName,
        launchDate = launchDate.format(dateTimeFormatter),
        imageUrl = links.missionPatchSmall,
        onClicked = { onLaunchClicked?.invoke(this) }
    )
}