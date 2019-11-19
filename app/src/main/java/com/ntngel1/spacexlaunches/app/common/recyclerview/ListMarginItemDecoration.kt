package com.ntngel1.spacexlaunches.app.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.app.utils.dp

class ListMarginItemDecoration(
    private val betweenElementsMargin: Int = 8.dp,
    private val startMargin: Int = betweenElementsMargin,
    private val leftMargin: Int = 8.dp,
    private val rightMargin: Int = leftMargin
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = (parent.layoutManager as LinearLayoutManager).getPosition(view)

        outRect.left = leftMargin
        outRect.right = rightMargin

        if (position == 0) {
            outRect.top = startMargin
        }

        if (position > 0) {
            outRect.top = betweenElementsMargin
        }
    }
}