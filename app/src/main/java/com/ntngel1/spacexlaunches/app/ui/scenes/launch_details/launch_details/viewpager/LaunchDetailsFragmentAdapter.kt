package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.viewpager

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_images.LaunchDetailsImagesFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_resources.LaunchDetailsResourcesFragment

class LaunchDetailsFragmentAdapter(
    private val launchId: Int,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int) = when (position) {
        IMAGES_POSITION -> LaunchDetailsImagesFragment.newInstance(launchId)
        RESOURCES_POSITION -> LaunchDetailsResourcesFragment.newInstance(launchId)
        else -> throw IllegalStateException("No such fragment for position = $position")
    }

    override fun getItemCount() = 2

    companion object {
        private const val IMAGES_POSITION = 0
        private const val RESOURCES_POSITION = 1
    }
}