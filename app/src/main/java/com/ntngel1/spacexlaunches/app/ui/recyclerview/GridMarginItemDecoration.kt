package com.ntngel1.spacexlaunches.app.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridMarginItemDecoration(
    private val topMargin: Int = 0,
    private val edgeMargin: Int = 0,
    private val betweenElementsHorizontalMargin: Int = 0,
    private val betweenElementsVerticalMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position % 2 == 0) {
            // Левые элементы
            outRect.right = betweenElementsHorizontalMargin / 2
            outRect.left = edgeMargin
        } else {
            // Правые элементы
            outRect.left = betweenElementsHorizontalMargin / 2
            outRect.right = edgeMargin
        }

        if (position != 0 && position != 1) {
            // Все элементы кроме первой строки
            outRect.top = betweenElementsVerticalMargin
        } else {
            // Первая строка элементов
            outRect.top = topMargin
        }
    }
}