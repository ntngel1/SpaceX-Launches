package com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common

abstract class ViewModelDiffer <in VM : ViewModel, out P : Payload> {

    /**
     * Сравнивает две модели по ID для DiffUtil
     */
    fun areItemsTheSame(old: VM, new: VM): Boolean {
        return old.id == new.id
    }

    /**
     * По дефолту всегда будет перебинживать ячейку
     */
    open fun areContentsTheSame(old: VM, new: VM): Boolean {
        return false
    }

    /**
     * DiffUtil Payloads
     * Вызывается, когда [areItemsTheSame] возвращает true, а [areContentsTheSame] - false
     * Нужно для того, чтобы не перебинживать всю View, а только отдельные элементы View
     * (TextView, ImageView), ...
     */
    open fun getChangePayload(old: VM, new: VM): List<P>? {
        return null
    }
}
