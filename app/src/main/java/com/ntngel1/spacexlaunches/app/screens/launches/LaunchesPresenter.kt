package com.ntngel1.spacexlaunches.app.screens.launches

import com.ntngel1.spacexlaunches.app.common.base.BasePresenter
import com.ntngel1.spacexlaunches.app.common.exception.AlreadyLoadedException
import com.ntngel1.spacexlaunches.app.common.exception.AlreadyLoadingException
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LaunchesPresenter @Inject constructor(
    private val launchGateway: LaunchGateway
) : BasePresenter<LaunchesView>() {

    private var launches = emptyList<LaunchEntity>()

    private var offset: Int = 0
    private var didLoadAllData = false
    private var isLoading = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadLaunches()
    }

    fun onLoadMoreLaunches() {
        if (!isLoading && !didLoadAllData) {
            loadLaunches()
        }
    }

    fun onRefreshLaunches() {
        compositeDisposable.clear()
        offset = 0
        didLoadAllData = false
        isLoading = false
        launches = emptyList()
        viewState.setLaunches(launches)

        loadLaunches()
    }

    fun onLaunchClicked(launch: LaunchEntity) {
        viewState.openLaunchDetailsScene(launch.flightNumber)
    }

    private fun loadLaunches() {
        // TODO Custom exception or not?
        if (isLoading) {
            throw AlreadyLoadingException()
        } else if (didLoadAllData) {
            throw AlreadyLoadedException()
        }

        launchGateway.getLaunchesWithDescendingLaunchDate(offset, LAUNCHES_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading = true

                if (isLoadingFirstPage()) {
                    viewState.setIsRefreshing(true)
                } else {
                    viewState.setIsProgressBarVisible(true)
                }
            }
            .doOnSuccess { fetchedLaunches ->
                launches = launches + fetchedLaunches
                viewState.setLaunches(launches)
            }
            .doFinally {
                isLoading = false
                viewState.setIsProgressBarVisible(false)
                viewState.setIsRefreshing(false)
            }
            .subscribe({ fetchedLaunches ->
                offset += fetchedLaunches.size

                if (fetchedLaunches.isEmpty()) {
                    didLoadAllData = true
                }
            }, {
                viewState.showLoadingError()
                it.printStackTrace()
            })
            .disposeOnDestroy()
    }

    private fun isLoadingFirstPage() = offset == 0

    companion object {
        const val LAUNCHES_LIMIT = 10
    }
}