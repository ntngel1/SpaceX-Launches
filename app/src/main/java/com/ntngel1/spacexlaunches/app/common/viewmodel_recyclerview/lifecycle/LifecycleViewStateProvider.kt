package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.lifecycle

import android.view.View
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.Payload
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewState
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.ViewStateProvider

abstract class LifecycleViewStateProvider<VS : ViewState, P : Payload> : ViewStateProvider<VS, P>() {
    open fun saveStateOnPause(itemView: View): ViewState? = null
    open fun saveStateOnDestroy(itemView: View): ViewState? = null
}