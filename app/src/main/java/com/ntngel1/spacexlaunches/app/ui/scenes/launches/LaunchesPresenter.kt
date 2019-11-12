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

    private fun loadLaunches() {
        if (isLoading || didLoadAllData) {
            return
        }

        launchGateway.getLaunchesWithDescendingLaunchDate(offset, LAUNCHES_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading = true
                viewState.setIsProgressBarVisible(true)
            }
            .doOnSuccess { fetchedLaunches ->
                offset += LAUNCHES_LIMIT

                if (fetchedLaunches.isEmpty()) {
                    didLoadAllData = true
                }
            }
            .doFinally {
                isLoading = false
                viewState.setIsProgressBarVisible(false)
            }
            .subscribe({ fetchedLaunches ->
                launches.addAll(fetchedLaunches)
                viewState.setLaunches(launches)
            }, {
                // TODO Handle error
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    companion object {
        const val LAUNCHES_LIMIT = 10
    }
}