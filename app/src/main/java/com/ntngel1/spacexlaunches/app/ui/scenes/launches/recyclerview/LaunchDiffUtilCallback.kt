package com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity

class LaunchDiffUtilCallback(
    private val oldLaunches: List<LaunchEntity>,
    private val newLaunches: List<LaunchEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldLaunches.size

    override fun getNewListSize(): Int = newLaunches.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLaunch = oldLaunches[oldItemPosition]
        val newLaunch = newLaunches[newItemPosition]

        return oldLaunch.missionName == newLaunch.missionName

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLaunch = oldLaunches[oldItemPosition]
        val newLaunch = newLaunches[newItemPosition]

        return oldLaunch.missionName == newLaunch.missionName &&
                oldLaunch.launchDate == newLaunch.launchDate &&
                oldLaunch.links.missionPatchSmall == newLaunch.links.missionPatchSmall
    }
}