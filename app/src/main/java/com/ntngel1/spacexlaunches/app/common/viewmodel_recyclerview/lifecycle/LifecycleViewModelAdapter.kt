package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewHolder
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewModelAdapter

class LifecycleViewModelAdapter(lifecycleOwner: LifecycleOwner): ViewModelAdapter(), LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }


    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        boundViewHolders.values.forEach(::onPauseHolder)
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        boundViewHolders.values.forEach(::onDestroyHolder)
    }

    private fun onPauseHolder(holder: ViewHolder) {
        val viewModel = getViewModel(holder)
        val binder = getViewBinder(viewModel)

        if (binder is LifecycleViewBinder) {
            saveViewStateOnPauseIfNeeded(holder)
            binder.onPause(viewModel, holder.itemView)
        }
    }

    private fun saveViewStateOnPauseIfNeeded(holder: ViewHolder) {
        val viewModel = getViewModel(holder)
        val viewStateProvider = getViewStateProvider(viewModel)

        if (viewStateProvider != null && viewStateProvider is LifecycleViewStateProvider) {
            val viewState = viewStateProvider.saveStateOnPause(holder.itemView)
                ?: return

            saveViewState(viewModel, viewState)
        }
    }

    private fun onDestroyHolder(holder: ViewHolder) {
        val viewModel = getViewModel(holder)
        val binder = getViewBinder(viewModel)

        if (binder is LifecycleViewBinder) {
            saveViewStateOnDestroyIfNeeded(holder)
            binder.onDestroy(viewModel, holder.itemView)
        }
    }

    private fun saveViewStateOnDestroyIfNeeded(holder: ViewHolder) {
        val viewModel = getViewModel(holder)
        val viewStateProvider = getViewStateProvider(viewModel)

        if (viewStateProvider != null && viewStateProvider is LifecycleViewStateProvider) {
            val viewState = viewStateProvider.saveStateOnDestroy(holder.itemView)
                ?: return

            saveViewState(viewModel, viewState)
        }
    }
}