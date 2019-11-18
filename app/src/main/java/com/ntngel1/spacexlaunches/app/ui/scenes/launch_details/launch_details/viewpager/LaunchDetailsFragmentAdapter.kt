package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_images.LaunchDetailsImagesFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_resources.LaunchDetailsResourcesFragment
import com.ntngel1.spacexlaunches.app.utils.str

class LaunchDetailsFragmentAdapter(
    private val flightNumber: Int,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment = when (position) {
        IMAGES_POSITION -> LaunchDetailsImagesFragment.newInstance(flightNumber)
        RESOURCES_POSITION -> LaunchDetailsResourcesFragment.newInstance(flightNumber)
        else -> throw IllegalStateException("No such fragment for position = $position")
    }

    override fun getItemCount() = 2

    companion object {
        const val IMAGES_POSITION = 0
        const val RESOURCES_POSITION = 1

        fun getTabLayoutMediator(tabLayout: TabLayout, pager: ViewPager2) =
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                val titleIdRes = when (position) {
                    IMAGES_POSITION -> R.string.images
                    RESOURCES_POSITION -> R.string.resources
                    else -> throw IllegalStateException("No such title for position = $position")
                }

                pager.setCurrentItem(tab.position, true)
                tab.text = tabLayout.context.str(titleIdRes)
            }
    }
}