package com.ntngel1.spacexlaunches.app.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    protected abstract val layoutId: Int

    var items = emptyList<T>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract fun createViewHolder(itemView: View): VH

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
            .let(::createViewHolder)

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }
}