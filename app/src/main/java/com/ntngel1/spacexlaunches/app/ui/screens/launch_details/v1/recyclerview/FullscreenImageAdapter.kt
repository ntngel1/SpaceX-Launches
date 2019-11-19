package com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.utils.loadImageWithProgressBar
import kotlinx.android.synthetic.main.item_image_fullscreen.view.*

class FullscreenImageAdapter : RecyclerView.Adapter<FullscreenImageAdapter.ViewHolder>() {

    var imageUrls = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = imageUrls.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_fullscreen, parent, false)
            .let(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetachedFromWindow()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(imageUrl: String) = with(itemView) {
            photo_image_fullscreen.loadImageWithProgressBar(imageUrl, progress_bar_image_fullscreen)
        }

        fun onDetachedFromWindow() = with(itemView) {
            photo_image_fullscreen.scale = 1f
        }
    }
}