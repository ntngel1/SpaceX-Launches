package com.ntngel1.spacexlaunches.app.ui.scenes.launches

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LaunchesPresenter @Inject constructor(
    private val launchGateway: LaunchGateway
) : MvpPresenter<LaunchesView>() {

    private val launches = arrayListOf<LaunchEntity>()

    private var offset: Int = 0
    private var didLoadAllData = false
    private var isLoading = false

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadLaunches()
    }

    fun onLoadMoreLaunches() {
        loadLaunches()
    }

    fun onRefreshLaunches() {
        compositeDisposable.clear()
        offset = 0
        didLoadAllData = false
        isLoading = false
        launches.clear()
        viewState.setLaunches(launches)

        loadLaunches()
    }

    fun onLaunchClicked(launch: LaunchEntity) {
        viewState.openLaunchDetailsScene(launch.flightNumber)
    }

    private fun loadLaunches() {
        if (isLoading || didLoadAllData) {
            return
        }

        launchGateway.getLaunchesWithDescendingLaunchDate(offset, LAUNCHES_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading = true

                if (offset > 0) {
                    viewState.setIsProgressBarVisible(true)
                } else {
                    viewState.setIsRefreshing(true)
                }
            }
            .doOnSuccess { fetchedLaunches ->
                launches.addAll(fetchedLaunches)
                viewState.setLaunches(launches)
            }
            .doFinally {
                isLoading = false
                viewState.setIsProgressBarVisible(false)
                viewState.setIsRefreshing(false)
            }
            .subscribe({ fetchedLaunches ->
                offset += LAUNCHES_LIMIT

                if (fetchedLaunches.isEmpty()) {
                    didLoadAllData = true
                }
            }, {
                viewState.showLoadingError()
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    companion object {
        const val LAUNCHES_LIMIT = 10
    }
}