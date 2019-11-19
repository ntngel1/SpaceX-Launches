package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common

interface ViewStateStore {
    fun hasViewState(viewModelId: String): Boolean
    fun getViewState(viewModelId: String): ViewState?
    fun saveViewState(viewModelId: String, viewState: ViewState)
    fun clearViewStates()
}