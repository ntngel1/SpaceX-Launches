package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.utils.loadImage

class ImageAdapter(
    private val onClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var imageUrls = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = imageUrls.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
            .let(::ViewHolder)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            onClicked.invoke(adapterPosition)
        }

        fun bind(imageUrl: String) {
            (itemView as ImageView).loadImage(imageUrl)
        }
    }
}