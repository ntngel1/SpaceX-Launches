package com.ntngel1.spacexlaunches.app.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.launch_details.LaunchDetailsView
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchesFragment : MvpAppCompatFragment(), LaunchesView {

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter() = App.appComponent.provideLaunchesPresenter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun setLaunches(launches: List<LaunchEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}