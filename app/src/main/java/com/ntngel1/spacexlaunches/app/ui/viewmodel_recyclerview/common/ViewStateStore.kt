package com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common

import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewState

interface ViewStateStore {
    fun hasViewState(viewModelId: String): Boolean
    fun getViewState(viewModelId: String): ViewState?
    fun saveViewState(viewModelId: String, viewState: ViewState)
    fun clearViewStates()
}