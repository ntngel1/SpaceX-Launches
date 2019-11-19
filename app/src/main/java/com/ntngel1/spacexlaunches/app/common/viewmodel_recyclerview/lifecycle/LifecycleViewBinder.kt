package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.lifecycle

import android.view.View
import com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common.*

abstract class LifecycleViewBinder<VM : ViewModel, out VD : ViewModelDiffer<VM, P>, P : Payload, VS : ViewState> : ViewBinder<VM, VD, P, VS>() {

    /**
     * Вызывается при паузе фрагмента/активити (сворачивание приложения)
     */
    open fun onPause(viewModel: VM, itemView: View) {}

    /**
     * Вызывается при уничтожении фрагмента/активити (повороты).
     */
    open fun onDestroy(viewModel: VM, itemView: View) {}
}