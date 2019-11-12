package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.utils.loadImage

class SmallImagesViewPagerAdapter(
    private val onImageClicked: (position: Int) -> Unit
) : PagerAdapter() {

    private var images = emptyList<String>()

    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = LayoutInflater.from(container.context)
            .inflate(R.layout.item_image_small, container, false) as ImageView

        imageView.setOnClickListener {
            onImageClicked.invoke(position)
        }

        imageView.loadImage(images[position])
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }
}