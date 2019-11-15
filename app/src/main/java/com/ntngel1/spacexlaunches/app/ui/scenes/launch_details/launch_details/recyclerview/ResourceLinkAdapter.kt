package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.utils.loadImage
import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import kotlinx.android.synthetic.main.item_resource_link.view.*

class ResourceLinkAdapter : RecyclerView.Adapter<ResourceLinkAdapter.ViewHolder>() {

    var links = emptyList<ResourceLinkEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = links.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resource_link, parent, false)
            .let(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(links[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(link: ResourceLinkEntity) = with(itemView) {
            image_preview.loadImage(link.previewImageUrl)

            text_title.text = link.title
            text_description.text = link.description
            text_url.text = link.url
        }
    }
}