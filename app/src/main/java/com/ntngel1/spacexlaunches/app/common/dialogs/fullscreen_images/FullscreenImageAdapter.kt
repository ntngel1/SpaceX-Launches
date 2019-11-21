package com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images

import android.view.View
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.common.base.BaseAdapter
import com.ntngel1.spacexlaunches.app.common.base.BaseViewHolder
import com.ntngel1.spacexlaunches.app.utils.loadImageWithProgressBar
import kotlinx.android.synthetic.main.item_image_fullscreen.view.*

class FullscreenImageAdapter : BaseAdapter<String, FullscreenImageAdapter.ViewHolder>() {

    override val layoutId: Int
        get() = R.layout.item_image_fullscreen

    override fun createViewHolder(itemView: View) = ViewHolder(itemView)

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

    inner class ViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {

        override fun bind(item: String) = with(itemView) {
            super.bind(item)
            photo_image_fullscreen.loadImageWithProgressBar(item, progressbar_image_fullscreen)
        }

        override fun unbind() = with(itemView) {
            super.unbind()
            photo_image_fullscreen.scale = 1f
        }
    }
}