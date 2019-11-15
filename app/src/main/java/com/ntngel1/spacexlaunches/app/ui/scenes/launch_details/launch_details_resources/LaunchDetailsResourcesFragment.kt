package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.utils.argument
import moxy.MvpAppCompatFragment

class LaunchDetailsResourcesFragment : MvpAppCompatFragment() {

    private val launchId: Int by argument(LAUNCH_ID_KEY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_launch_details_resources, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val LAUNCH_ID_KEY = "launch_id"

        fun newInstance(launchId: Int) = LaunchDetailsResourcesFragment().apply {
            arguments = bundleOf(LAUNCH_ID_KEY to launchId)
        }
    }
}