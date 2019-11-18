package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_images

import com.ntngel1.spacexlaunches.app.ui.base.BasePresenter
import com.ntngel1.spacexlaunches.app.utils.plusAssign
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LaunchDetailsImagesPresenter @Inject constructor(
    private val launchGateway: LaunchGateway
): BasePresenter<LaunchDetailsImagesView>() {

    // Args
    var flightNumber = -1

    private var launch: LaunchEntity? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchImages()
    }

    fun onImageClicked(position: Int) {
        launch?.let { launch ->
            viewState.showImagesFullscreen(launch.links.flickrImages, position)
        }
    }

    private fun fetchImages() {
        compositeDisposable += launchGateway.getLaunchByFlightNumber(flightNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ fetchedLaunch ->
                launch = fetchedLaunch
                viewState.setImages(fetchedLaunch.links.flickrImages)
            }, {
                it.printStackTrace()
            })
    }
}