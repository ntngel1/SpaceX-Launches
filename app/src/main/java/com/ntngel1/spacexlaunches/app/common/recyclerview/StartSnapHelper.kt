package com.ntngel1.spacexlaunches.app.common.recyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper

class StartSnapHelper : LinearSnapHelper() {

    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)

        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else {
            out[1] = 0
        }

        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager) =
        if (layoutManager is LinearLayoutManager) {
            getStartView(
                layoutManager,
                if (layoutManager.canScrollHorizontally()) getHorizontalHelper(layoutManager)
                else getVerticalHelper(layoutManager)
            )
        } else {
            super.findSnapView(layoutManager)
        }

    private fun distanceToStart(targetView: View, helper: OrientationHelper) =
        helper.getDecoratedStart(targetView) - helper.startAfterPadding

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {
        if (layoutManager !is LinearLayoutManager) {
            return super.findSnapView(layoutManager)
        }

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val isLastItemVisible = run {
            val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

            lastVisibleItemPosition == layoutManager.getItemCount() - 1
        }

        val child = layoutManager.findViewByPosition(firstVisibleItemPosition)

        return when {
            firstVisibleItemPosition == RecyclerView.NO_POSITION || isLastItemVisible -> null

            helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 &&
                    helper.getDecoratedEnd(child) > 0 -> child

            else -> layoutManager.findViewByPosition(firstVisibleItemPosition + 1)
        }
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager) =
        verticalHelper ?: run {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
            verticalHelper!!
        }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager) =
        horizontalHelper ?: run {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
            horizontalHelper!!
        }
}