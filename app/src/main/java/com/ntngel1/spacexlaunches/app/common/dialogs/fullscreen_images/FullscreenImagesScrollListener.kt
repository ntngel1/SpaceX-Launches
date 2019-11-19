package com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FullscreenImagesScrollListener(
    private val onImageScrolled: (currentPosition: Int) -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
            val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

            if (currentPosition != RecyclerView.NO_POSITION) {
                onImageScrolled.invoke(currentPosition)
            }
        }
    }
}