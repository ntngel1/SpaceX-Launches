package com.ntngel1.spacexlaunches.app.screens.launch_details

import com.ntngel1.spacexlaunches.app.common.base.BasePresenter
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LaunchDetailsPresenter @Inject constructor(
    private val launchGateway: LaunchGateway
): BasePresenter<LaunchDetailsView>() {

    // Args
    var flightNumber = -1

    lateinit var launch: LaunchEntity

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
        launchGateway.getLaunchByFlightNumber(flightNumber)
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
            .disposeOnDestroy()
    }
}