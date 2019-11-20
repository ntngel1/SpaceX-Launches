package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.resources

import com.ntngel1.spacexlaunches.app.common.base.BasePresenter
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import com.ntngel1.spacexlaunches.domain.gateway.ResourceLinkGateway
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LaunchDetailsResourcesPresenter @Inject constructor(
    private val launchGateway: LaunchGateway,
    private val resourceLinkGateway: ResourceLinkGateway
) : BasePresenter<LaunchDetailsResourcesView>() {

    // Args
    var flightNumber: Int = -1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchLaunch()
    }

    fun onLinkClicked(link: ResourceLinkEntity) {
        viewState.openUrl(link.url)
    }

    fun onTryAgainClicked() {
        fetchLaunch()
    }

    private fun fetchLaunch() {
        launchGateway.getLaunchByFlightNumber(flightNumber)
            .flatMap { launch ->
                fetchLinks(launch)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.setIsLoading(true)
                viewState.setIsLoadingError(false)
            }
            .doFinally {
                viewState.setIsLoading(false)
            }
            .subscribe({ resourceLinks ->
                if (resourceLinks.isNotEmpty()) {
                    viewState.setResourceLinks(resourceLinks)
                } else {
                    viewState.setIsStubVisible(true)
                }
            }, {
                viewState.setIsLoadingError(true)
                it.printStackTrace()
            })
            .disposeOnDestroy()
    }

    private fun fetchLinks(launch: LaunchEntity): Single<List<ResourceLinkEntity>> {
        val urls = with(launch.links) {
            listOfNotNull(redditMedia, wikipedia, youtube, article)
        }

        val resourceLinksSources = urls.map { url ->
            resourceLinkGateway.getResourceLink(url)
        }

        return Single.merge(resourceLinksSources)
            .toList()
    }
}