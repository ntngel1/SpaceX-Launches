package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.recyclerview.ResourceLinkAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.setIsVisible
import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import kotlinx.android.synthetic.main.fragment_launch_details_resources.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchDetailsResourcesFragment : MvpAppCompatFragment(), LaunchDetailsResourcesView {

    private val flightNumber: Int by argument(FLIGHT_NUMBER_KEY)

    private val linksAdapter = ResourceLinkAdapter()

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsResourcesPresenter

    @ProvidePresenter
    fun provideLaunchDetailsResourcesPresenter() =
        App.appComponent.provideLaunchDetailsResourcesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.flightNumber = flightNumber
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_launch_details_resources, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLinksRecyclerView()
    }

    override fun setIsLoading(isLoading: Boolean) {
        progressBar.setIsVisible(isLoading)
        recycler_links.setIsVisible(!isLoading)
    }

    override fun setResourceLinks(links: List<ResourceLinkEntity>) {
        linksAdapter.links = links
    }

    private fun setupLinksRecyclerView() {
        with(recycler_links) {
            adapter = linksAdapter
            addItemDecoration(ListMarginItemDecoration())
        }
    }

    companion object {
        private const val FLIGHT_NUMBER_KEY = "flight_number"

        fun newInstance(launchId: Int) = LaunchDetailsResourcesFragment().apply {
            arguments = bundleOf(FLIGHT_NUMBER_KEY to launchId)
        }
    }
}