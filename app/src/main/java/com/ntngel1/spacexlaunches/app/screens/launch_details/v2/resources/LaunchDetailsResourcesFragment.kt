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
import com.ntngel1.spacexlaunches.app.common.base.BaseFragment
import com.ntngel1.spacexlaunches.app.common.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview.ResourceLinkAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.visibleOrGone
import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import kotlinx.android.synthetic.main.fragment_launch_details_resources.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchDetailsResourcesFragment : BaseFragment(), LaunchDetailsResourcesView {

    override val layoutId: Int
        get() = R.layout.fragment_launch_details_resources

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLinksRecyclerView()

        button_launch_details_resources_try_again.setOnClickListener {
            presenter.onTryAgainClicked()
        }
    }

    override fun setResourceLinks(links: List<ResourceLinkEntity>) {
        linksAdapter.items = links
    }

    override fun openUrl(url: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .let(::startActivity)

    override fun setIsStubVisible(isVisible: Boolean) {
        text_launch_details_resources_stub.visibleOrGone(isVisible)
    }

    override fun setIsLoading(isLoading: Boolean) {
        progressbar_launch_details_resources.visibleOrGone(isLoading)
        recycler_launch_details_resources.visibleOrGone(!isLoading)
    }

    override fun setIsLoadingError(isLoadingError: Boolean) {
        linearlayout_launch_details_resources_loading_error.visibleOrGone(isLoadingError)
    }

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