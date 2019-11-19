package com.ntngel1.spacexlaunches.app.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.app.utils.dp

class CarouselMarginItemDecoration(
    private val firstAndLastElementMargin: Int = 16.dp,
    private val betweenElementsMargin: Int = 8.dp
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val totalItemsCount = parent.adapter?.itemCount ?: 0

        // Первый элемент
        if (position == 0) {
            outRect.left = firstAndLastElementMargin
        }

        // Последний элемент
        if (position == totalItemsCount - 1) {
            outRect.left = betweenElementsMargin
            outRect.right = firstAndLastElementMargin
        }

        // Все остальные элементы
        if (position in 1 until totalItemsCount) {
            outRect.left = betweenElementsMargin
        }
    }
}