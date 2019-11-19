package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.images.LaunchDetailsImagesFragment
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.resources.LaunchDetailsResourcesFragment
import com.ntngel1.spacexlaunches.app.utils.str

class LaunchDetailsFragmentAdapter(
    private val flightNumber: Int,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment = when (position) {
        RESOURCES_POSITION -> LaunchDetailsResourcesFragment.newInstance(flightNumber)
        IMAGES_POSITION -> LaunchDetailsImagesFragment.newInstance(flightNumber)
        else -> throw IllegalStateException("No such fragment for position = $position")
    }

    override fun getItemCount() = 2

    companion object {
        const val RESOURCES_POSITION = 0
        const val IMAGES_POSITION = 1

        fun getTabLayoutMediator(tabLayout: TabLayout, pager: ViewPager2) =
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                val titleIdRes = when (position) {
                    RESOURCES_POSITION -> R.string.resources
                    IMAGES_POSITION -> R.string.images
                    else -> throw IllegalStateException("No such title for position = $position")
                }

                pager.setCurrentItem(tab.position, true)
                tab.text = tabLayout.context.str(titleIdRes)
            }
    }
}