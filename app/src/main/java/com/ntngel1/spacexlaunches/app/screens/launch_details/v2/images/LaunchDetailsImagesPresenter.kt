package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.images

import com.ntngel1.spacexlaunches.app.common.base.BasePresenter
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

    fun onTryAgainClicked() {
        fetchImages()
    }

    private fun fetchImages() {
        launchGateway.getLaunchByFlightNumber(flightNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.setIsLoading(true)
                viewState.setIsLoadingError(false)
            }
            .doFinally {
                viewState.setIsLoading(false)
            }
            .subscribe({ fetchedLaunch ->
                launch = fetchedLaunch

                if (fetchedLaunch.links.flickrImages.isNotEmpty()) {
                    viewState.setImages(fetchedLaunch.links.flickrImages)
                } else {
                    viewState.setIsStubVisible(true)
                }
            }, {
                viewState.setIsLoadingError(true)
                it.printStackTrace()
            })
            .disposeOnDestroy()
    }
}