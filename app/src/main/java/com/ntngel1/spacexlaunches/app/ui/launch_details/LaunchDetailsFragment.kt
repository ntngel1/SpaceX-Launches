package com.ntngel1.spacexlaunches.app.ui.launch_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchDetailsFragment : MvpAppCompatFragment(), LaunchDetailsView {

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter() = App.appComponent.provideLaunchDetailsPresenter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_details, container, false)
    }
}