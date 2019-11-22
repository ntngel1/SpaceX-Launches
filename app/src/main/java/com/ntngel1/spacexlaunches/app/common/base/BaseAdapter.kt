package com.ntngel1.spacexlaunches.app.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    var items = emptyList<T>()
        set(value) {
            val diff = calculateDiff(value)
            field = value
            diff.dispatchUpdatesTo(this)
        }

    abstract fun createViewHolder(itemView: View): VH

    open fun areItemsTheSame(oldItem: T, newItem: T) = false

    open fun areContentsTheSame(oldItem: T, newItem: T) = false

    final override fun getItemCount() = items.size

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
            .let(::createViewHolder)

    final override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    final override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    private fun calculateDiff(newItems: List<T>) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize() = items.size

            override fun getNewListSize() = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(items[oldItemPosition], newItems[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(items[oldItemPosition], newItems[newItemPosition])

        })

}