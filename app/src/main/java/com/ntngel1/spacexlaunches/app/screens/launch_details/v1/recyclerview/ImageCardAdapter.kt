package com.ntngel1.spacexlaunches.app.screens.launch_details.v1.recyclerview

import android.view.View
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.common.base.BaseAdapter
import com.ntngel1.spacexlaunches.app.common.base.BaseViewHolder
import com.ntngel1.spacexlaunches.app.utils.loadImageWithPlaceholder
import kotlinx.android.synthetic.main.item_image_card.view.*

class ImageCardAdapter(
    private val onClicked: (position: Int) -> Unit
) : BaseAdapter<String, ImageCardAdapter.ViewHolder>() {

    override val layoutId: Int
        get() = R.layout.item_image_card

    override fun createViewHolder(itemView: View) = ViewHolder(itemView)

    inner class ViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {

        init {
            itemView.setOnClickListener {
                onClicked.invoke(adapterPosition)
            }
        }

        override fun bind(item: String) {
            super.bind(item)
            itemView.image_image_card.loadImageWithPlaceholder(item)
        }
    }
}