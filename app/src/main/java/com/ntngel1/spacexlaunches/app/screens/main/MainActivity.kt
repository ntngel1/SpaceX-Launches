package com.ntngel1.spacexlaunches.app.screens.main

import android.os.Bundle
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = App.appComponent.mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
