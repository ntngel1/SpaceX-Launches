package com.ntngel1.spacexlaunches.app.common.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected lateinit var item: T

    @CallSuper
    open fun bind(item: T) {
        this.item = item
    }

    @CallSuper
    open fun unbind() {}
}