package com.ntngel1.spacexlaunches.app.ui.scenes.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.adapters.LaunchAdapter
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
    }

    private fun setupRecyclerView() {
        with(launchRecyclerView) {
            adapter = launchAdapter
        }
    }

    override fun setLaunches(launches: List<LaunchEntity>) {
        launchAdapter.setLaunches(launches)
    }

    private fun onLaunchClicked(launch: LaunchEntity) {
        TODO()
    }
}