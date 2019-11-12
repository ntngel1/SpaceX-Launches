package com.ntngel1.spacexlaunches.app.ui.main

import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.openLaunchesScreen()
    }
}