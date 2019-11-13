package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.utils.loadImage
import kotlinx.android.synthetic.main.item_image_card.view.*

class ImageCardAdapter(
    private val onClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<ImageCardAdapter.ViewHolder>() {

    var images = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged() // TODO DiffUtil?
        }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_card, parent, false)
            .let(::ViewHolder)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClicked.invoke(adapterPosition)
            }
        }

        fun bind(imageUrl: String) {
            itemView.imageView.loadImage(imageUrl)
        }
    }
}