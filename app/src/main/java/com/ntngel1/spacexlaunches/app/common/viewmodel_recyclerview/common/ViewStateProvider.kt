package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common

import android.view.View

abstract class ViewStateProvider<VS : ViewState, in P : Payload> {

    abstract fun saveState(itemView: View): VS
    abstract fun restoreState(viewState: VS, itemView: View)

    open fun updateState(viewState: VS, payloads: List<P>): VS? {
        return null
    }
}