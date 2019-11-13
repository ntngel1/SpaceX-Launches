package com.ntngel1.spacexlaunches.app.ui.scenes.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchAdapter
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchItemDecoration
import com.ntngel1.spacexlaunches.app.utils.setIsVisible
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launches.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchesFragment : MvpAppCompatFragment(), LaunchesView {

    @InjectPresenter
    internal lateinit var presenter: LaunchesPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter() = App.appComponent.provideLaunchesPresenter()

    private val launchAdapter = LaunchAdapter(::onLaunchClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        swipeRefreshLayout.setOnRefreshListener {
            presenter.onRefreshLaunches()
        }
    }

    override fun setLaunches(launches: List<LaunchEntity>) {
        launchAdapter.setLaunches(launches)
    }

    override fun setIsProgressBarVisible(isVisible: Boolean) {
        progressBar.setIsVisible(isVisible)
    }

    override fun setIsRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    override fun openLaunchDetailsScene(flightNumber: Int) {
        LaunchesFragmentDirections.openLaunchDetailsScene(flightNumber)
            .let(findNavController()::navigate)
    }

    private fun setupRecyclerView() {
        with(launchRecyclerView) {
            adapter = launchAdapter
            addItemDecoration(LaunchItemDecoration())

            val paginationScrollListener = PaginationScrollListener(
                LaunchesPresenter.LAUNCHES_LIMIT,
                presenter::onLoadMoreLaunches
            )

            addOnScrollListener(paginationScrollListener)
        }
    }

    private fun onLaunchClicked(launch: LaunchEntity) {
        presenter.onLaunchClicked(launch)
    }
}