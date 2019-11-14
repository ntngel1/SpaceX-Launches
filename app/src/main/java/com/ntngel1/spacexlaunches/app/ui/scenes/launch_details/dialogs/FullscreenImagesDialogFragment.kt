package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.recyclerview.FullscreenImageAdapter
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.viewpager.FullscreenImagesPagerAdapter
import kotlinx.android.synthetic.main.dialog_fullscreen_images.*

class FullscreenImagesDialogFragment(
    private val images: List<String>,
    private val offset: Int = 0
) : DialogFragment() {

    init {
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fullscreen_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            dismiss()
        }

        setupImagesRecyclerView()
    }

    private fun setupImagesRecyclerView() {
        imagesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        PagerSnapHelper().attachToRecyclerView(imagesRecyclerView)

        imagesRecyclerView.adapter = FullscreenImageAdapter().apply {
            images = this@FullscreenImagesDialogFragment.images
        }

        imagesRecyclerView.scrollToPosition(offset)
    }
}