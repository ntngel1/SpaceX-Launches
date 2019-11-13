package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.viewpager.FullscreenImagesPagerAdapter
import kotlinx.android.synthetic.main.dialog_fullscreen_images.*

class FullscreenImagesDialogFragment(
    private val images: List<String>,
    private val offset: Int = 0,
    private val onClosed: (position: Int) -> Unit
) : DialogFragment() {

    init {
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
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
            onClosed.invoke(imagesViewPager.currentItem)
            dismiss()
        }

        setupImagesViewPager()
    }

    private fun setupImagesViewPager() {
        imagesViewPager.adapter = FullscreenImagesPagerAdapter(images)
        imagesViewPager.setCurrentItem(offset, false)
    }
}