package com.ntngel1.spacexlaunches.app.screens.launch_details.launch_details.dialogs.fullscreen_images

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.screens.launch_details.launch_details.recyclerview.FullscreenImageAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.setupToolbar
import com.ntngel1.spacexlaunches.app.utils.str
import kotlinx.android.synthetic.main.dialog_fullscreen_images.*

class FullscreenImagesDialogFragment : DialogFragment() {

    private val params by argument<FullscreenImagesParams>(
        PARAMS_KEY
    )

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

        val toolbarTitle = params.images[params.offset].title
            ?: context?.str(R.string.imageFormat, params.offset + 1)

        toolbar.setupToolbar(
            title = toolbarTitle,
            titleColor = Color.WHITE,
            navigationIconId = R.drawable.ic_arrow_back_white_24dp
        ) {
            dismiss()
        }

        setupImagesRecyclerView()
    }

    private fun setupImagesRecyclerView() {
        imagesRecyclerView.adapter = FullscreenImageAdapter().apply {
            imageUrls = params.images.map { it.url }
        }


        val scrollListener = FullscreenImagesScrollListener { currentPosition ->
            toolbar.title = params.images[currentPosition].title
                ?: str(R.string.imageFormat, currentPosition + 1)
        }

        imagesRecyclerView.addOnScrollListener(scrollListener)
        PagerSnapHelper().attachToRecyclerView(imagesRecyclerView)

        imagesRecyclerView.scrollToPosition(params.offset)
    }

    companion object {
        private const val PARAMS_KEY = "params"

        fun newInstance(params: FullscreenImagesParams) = FullscreenImagesDialogFragment().apply {
            arguments = bundleOf(PARAMS_KEY to params)
        }
    }
}