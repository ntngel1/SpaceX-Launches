package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview

import android.view.View
import android.widget.ImageView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.common.base.BaseAdapter
import com.ntngel1.spacexlaunches.app.common.base.BaseViewHolder
import com.ntngel1.spacexlaunches.app.utils.loadImageWithPlaceholder

class ImageAdapter(
    private val onClicked: (position: Int) -> Unit
) : BaseAdapter<String, ImageAdapter.ViewHolder>() {

    override val layoutId: Int
        get() = R.layout.item_image

    override fun createViewHolder(itemView: View) = ViewHolder(itemView)

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

    inner class ViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {

        init {
            itemView.setOnClickListener {
                onClicked.invoke(adapterPosition)
            }
        }

        override fun bind(item: String) {
            super.bind(item)
            (itemView as ImageView).loadImageWithPlaceholder(item)
        }
    }
}