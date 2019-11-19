package com.ntngel1.spacexlaunches.app.common.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    protected val compositeDisposable = CompositeDisposable()

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    // TODO use this
    protected fun Disposable.disposeOnDestroy() {
        compositeDisposable.add(this)
    }
}