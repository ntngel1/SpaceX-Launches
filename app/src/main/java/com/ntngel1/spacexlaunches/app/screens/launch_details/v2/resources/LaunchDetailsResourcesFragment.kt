package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.resources

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.common.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview.ResourceLinkAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.setVisibleOrGone
import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import kotlinx.android.synthetic.main.fragment_launch_details_resources.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchDetailsResourcesFragment : MvpAppCompatFragment(), LaunchDetailsResourcesView {

    private val flightNumber: Int by argument(FLIGHT_NUMBER_KEY)

    private val linksAdapter = ResourceLinkAdapter(::onLinkClicked)

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsResourcesPresenter

    @ProvidePresenter
    fun provideLaunchDetailsResourcesPresenter() = App.appComponent.launchDetailsResourcesPresenter

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
        progressbar_launch_details_resources.setVisibleOrGone(isLoading)
        recycler_launch_details_resources.setVisibleOrGone(!isLoading)
    }

    override fun setResourceLinks(links: List<ResourceLinkEntity>) {
        linksAdapter.links = links
    }

    override fun openUrl(url: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .let(::startActivity)

    private fun setupLinksRecyclerView() {
        with(recycler_launch_details_resources) {
            adapter = linksAdapter
            addItemDecoration(ListMarginItemDecoration())
        }
    }

    private fun onLinkClicked(link: ResourceLinkEntity) {
        presenter.onLinkClicked(link)
    }

    companion object {
        private const val FLIGHT_NUMBER_KEY = "flight_number"

        fun newInstance(launchId: Int) = LaunchDetailsResourcesFragment().apply {
            arguments = bundleOf(FLIGHT_NUMBER_KEY to launchId)
        }
    }
}