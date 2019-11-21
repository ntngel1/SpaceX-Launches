package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.common.base.BaseAdapter
import com.ntngel1.spacexlaunches.app.common.base.BaseViewHolder
import com.ntngel1.spacexlaunches.app.utils.*
import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import kotlinx.android.synthetic.main.item_resource_link.view.*

class ResourceLinkAdapter(
    private val onClicked: (link: ResourceLinkEntity) -> Unit
) : BaseAdapter<ResourceLinkEntity, ResourceLinkAdapter.ViewHolder>() {

    override val layoutId: Int
        get() = R.layout.item_resource_link

    override fun createViewHolder(itemView: View) = ViewHolder(itemView)

    override fun areContentsTheSame(
        oldItem: ResourceLinkEntity,
        newItem: ResourceLinkEntity
    ) = oldItem.title == newItem.title &&
            oldItem.description == newItem.description &&
            oldItem.previewImageUrl == newItem.previewImageUrl &&
            oldItem.url == newItem.url

    inner class ViewHolder(itemView: View) : BaseViewHolder<ResourceLinkEntity>(itemView) {

        init {
            itemView.setOnClickListener {
                onClicked.invoke(item)
            }
        }

        override fun bind(item: ResourceLinkEntity) = with(itemView) {
            super.bind(item)
            image_resource_link_preview.loadImageOrGone(item.previewImageUrl)

            text_resource_link_title.text = if (item.title.isNullOrBlank()) {
                context.str(R.string.launch_details_v2_external_link)
            } else {
                item.title
            }

            text_resource_link_description.setTextOrGone(item.description)
            text_resource_link_url.text = item.url
        }
    }
}