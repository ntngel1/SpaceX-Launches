package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details

import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LaunchDetailsPresenter @Inject constructor(
    private val launchGateway: LaunchGateway
): MvpPresenter<LaunchDetailsView>() {

    var launchId = -1

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadLaunch()
    }

    private fun loadLaunch() {
        launchGateway.getLaunchByFlightNumber(launchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.setProgressBarIsVisible(true)
            }
            .doFinally {
                viewState.setProgressBarIsVisible(false)
            }
            .subscribe({ launch ->
                viewState.showLaunchDetails(launch)
            }, {
                // TODO Handle error
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }
}