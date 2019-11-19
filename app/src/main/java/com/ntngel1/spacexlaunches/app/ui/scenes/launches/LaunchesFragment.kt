package com.ntngel1.spacexlaunches.app.ui.scenes.launches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.di.launches.LaunchesScreenComponent
import com.ntngel1.spacexlaunches.app.di.launches.RecyclerViewLaunchesScreenModule
import com.ntngel1.spacexlaunches.app.ui.recyclerview.ListMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.recyclerview.PaginationScrollListener
import com.ntngel1.spacexlaunches.app.ui.recyclerview.progress_bar.ProgressBarViewBinder
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchItemDecoration
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.LaunchesSceneController
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.launch.LaunchViewBinder
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview.year.YearViewBinder
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModelAdapter
import com.ntngel1.spacexlaunches.app.utils.dp
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launches.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LaunchesFragment : MvpAppCompatFragment(), LaunchesView {

    lateinit var component: LaunchesScreenComponent

    @Inject
    lateinit var launchesSceneController: LaunchesSceneController

    @Inject
    lateinit var launchesAdapter: ViewModelAdapter

    @InjectPresenter
    internal lateinit var presenter: LaunchesPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter(): LaunchesPresenter {
        return component.presenter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = App.appComponent.launchesScreenComponentBuilder
            .recyclerViewModule(RecyclerViewLaunchesScreenModule(::onLoadMoreLaunches))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
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
        if (flightNumber % 2 == 0) {
            LaunchesFragmentDirections.openLaunchDetailsLive3Screen(flightNumber)
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
            addItemDecoration(component.marginItemDecoration)
            addOnScrollListener(component.paginationScrollListener)
        }

        launchesSceneController.onLaunchClicked = ::onLaunchClicked
    }

    private fun onLaunchClicked(launch: LaunchEntity) {
        presenter.onLaunchClicked(launch)
    }

    private fun onLoadMoreLaunches() {
        presenter.onLoadMoreLaunches()
    }
}