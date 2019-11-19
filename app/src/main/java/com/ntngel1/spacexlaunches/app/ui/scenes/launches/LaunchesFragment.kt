package com.ntngel1.spacexlaunches.app.ui.scenes.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.ui.recyclerview.progress_bar.ProgressBarViewBinder
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchAdapter
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchItemDecoration
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchesSceneController
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.launch.LaunchViewBinder
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.month.MonthViewBinder
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModelAdapter
import com.ntngel1.spacexlaunches.app.utils.setVisibleOrGone
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launches.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LaunchesFragment : MvpAppCompatFragment(), LaunchesView {

    @Inject
    lateinit var launchesSceneController: LaunchesSceneController

    private val launchesAdapter = ViewModelAdapter().apply {
        registerViewBinder(LaunchViewBinder())
        registerViewBinder(ProgressBarViewBinder())
        registerViewBinder(MonthViewBinder())
    }

    @InjectPresenter
    internal lateinit var presenter: LaunchesPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter() = App.appComponent.provideLaunchesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

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
        launchesSceneController.launches = launches
        recycler_launches.buildScene()
    }

    override fun setIsProgressBarVisible(isVisible: Boolean) {
        launchesSceneController.isProgressBarVisible = isVisible
        recycler_launches.buildScene()
    }

    override fun setIsRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    override fun openLaunchDetailsScene(flightNumber: Int) {
        // Fixme Kostil
        if (flightNumber % 2 == 0) {
            LaunchesFragmentDirections.openLaunchDetailsScene(flightNumber)
                .let(findNavController()::navigate)
        } else {
            LaunchesFragmentDirections.openLaunchDetailsKinoplanScreen(flightNumber)
                .let(findNavController()::navigate)
        }
    }

    override fun showLoadingError() {
        Toast.makeText(context, getString(R.string.unableToLoadLaunches), Toast.LENGTH_LONG)
            .show()
    }

    private fun setupRecyclerView() {
        with(recycler_launches) {
            adapter = launchesAdapter

            attachSceneController(launchesSceneController)
            addItemDecoration(LaunchItemDecoration())

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