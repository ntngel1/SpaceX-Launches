package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.epoxy

import android.util.SparseArray
import androidx.recyclerview.widget.RecyclerView
import java.util.*


/**
 * Like its parent, UnboundedViewPool lets you share Views between multiple RecyclerViews. However
 * there is no maximum number of recycled views that it will store. This usually ends up being
 * optimal, barring any hard memory constraints, as RecyclerViews do not recycle more Views than
 * they need.
 */
internal class UnboundedViewPool : RecyclerView.RecycledViewPool() {

    private val scrapHeaps = SparseArray<Queue<RecyclerView.ViewHolder>>()

    override fun clear() {
        scrapHeaps.clear()
    }

    override fun setMaxRecycledViews(viewType: Int, max: Int) {
        throw UnsupportedOperationException(
            "UnboundedViewPool does not support setting a maximum number of recycled views"
        )
    }

    override fun getRecycledView(viewType: Int): RecyclerView.ViewHolder? {
        val scrapHeap = scrapHeaps.get(viewType)
        return scrapHeap?.poll()
    }

    override fun putRecycledView(viewHolder: RecyclerView.ViewHolder) {
        getScrapHeapForType(viewHolder.itemViewType).add(viewHolder)
    }

    private fun getScrapHeapForType(viewType: Int): Queue<RecyclerView.ViewHolder> {
        var scrapHeap: Queue<RecyclerView.ViewHolder>? = scrapHeaps.get(viewType)
        if (scrapHeap == null) {
            scrapHeap = LinkedList()
            scrapHeaps.put(viewType, scrapHeap)
        }
        return scrapHeap
    }
}
