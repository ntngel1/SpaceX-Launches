package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
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

    // Args
    var launchId = -1

    lateinit var launch: LaunchEntity

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadLaunch()
    }

    fun onFlickrImageClicked(position: Int) {
        viewState.showImagesFullscreen(launch.links.flickrImages, position)
    }

    fun onMissionPatchClicked() {
        launch.links.missionPatch?.let { missionPatch ->
            viewState.showImageWithTitle(missionPatch, launch.missionName)
        }
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
            .subscribe({ fetchedLaunch ->
                launch = fetchedLaunch
                viewState.showLaunchDetails(fetchedLaunch)
            }, {
                viewState.navigateBackWithLoadingError()
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }
}