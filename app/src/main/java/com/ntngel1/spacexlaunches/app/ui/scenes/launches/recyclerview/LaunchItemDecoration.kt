package com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.app.utils.dp

class LaunchItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 8.dp
        outRect.left = 8.dp
        outRect.right = 8.dp
    }
}