package com.ntngel1.spacexlaunches.app.screens.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.common.base.BaseFragment
import com.ntngel1.spacexlaunches.app.common.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.common.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.common.recyclerview.progress_bar.ProgressBarViewBinder
import com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.LaunchesSceneController
import com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.launch.LaunchViewBinder
import com.ntngel1.spacexlaunches.app.screens.launches.recyclerview.year.YearViewBinder
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModelAdapter
import com.ntngel1.spacexlaunches.app.utils.dp
import com.ntngel1.spacexlaunches.app.utils.toast
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launches.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LaunchesFragment : BaseFragment(), LaunchesView {

    override val layoutId: Int
        get() = R.layout.fragment_launches

    @Inject
    lateinit var launchesSceneController: LaunchesSceneController

    @InjectPresenter
    internal lateinit var presenter: LaunchesPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter() = App.appComponent.launchesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        swipe_refresh_layout_launches.setOnRefreshListener {
            presenter.onRefreshLaunches()
        }
    }

    override fun setLaunches(launches: List<LaunchEntity>) {
        launchesSceneController.launches = launches
        recycler_launches.buildScene()
    }

    override fun setIsProgressBarVisible(isVisible: Boolean) {
        launchesSceneController.isProgressBarVisible = isVisible
        recycler_launches.buildScene()
    }

    override fun setIsRefreshing(isRefreshing: Boolean) {
        swipe_refresh_layout_launches.isRefreshing = isRefreshing
    }

    override fun openLaunchDetailsScene(flightNumber: Int) {
        // TODO?
        if (flightNumber % 2 == 0) {
            LaunchesFragmentDirections.openLaunchDetailsV1Screen(flightNumber)
                .let(findNavController()::navigate)
        } else {
            LaunchesFragmentDirections.openLaunchDetailsV2Screen(flightNumber)
                .let(findNavController()::navigate)
        }
    }

    override fun showLoadingError() {
        toast(R.string.launches_unable_to_load_launches)
    }

    private fun setupRecyclerView() {
        with(recycler_launches) {
            attachSceneController(launchesSceneController)

            val marginItemDecoration = ListMarginItemDecoration(
                betweenElementsMargin = 8.dp,
                startMargin = 8.dp,
                leftMargin = 8.dp,
                rightMargin = 8.dp
            )

            addItemDecoration(marginItemDecoration)

            val paginationScrollListener = PaginationScrollListener(
                LaunchesPresenter.LAUNCHES_LIMIT,
                presenter::onLoadMoreLaunches
            )

            addOnScrollListener(paginationScrollListener)
        }

        launchesSceneController.onLaunchClicked = ::onLaunchClicked
    }

    private fun onLaunchClicked(launch: LaunchEntity) {
        presenter.onLaunchClicked(launch)
    }
}