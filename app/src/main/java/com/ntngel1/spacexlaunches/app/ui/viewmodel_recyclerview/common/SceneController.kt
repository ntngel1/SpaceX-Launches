package com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common

import androidx.collection.ArrayMap

abstract class SceneController : ViewStateStore {

    abstract fun buildViewModels(): List<ViewModel>


    private val viewStates = ArrayMap<String, ViewState>()

    override fun hasViewState(viewModelId: String): Boolean {
        return viewStates.contains(viewModelId)
    }

    override fun clearViewStates() {
        return viewStates.clear()
    }

    override fun getViewState(viewModelId: String): ViewState? {
        return viewStates[viewModelId]
    }

    override fun saveViewState(viewModelId: String, viewState: ViewState) {
        viewStates[viewModelId] = viewState
    }
}