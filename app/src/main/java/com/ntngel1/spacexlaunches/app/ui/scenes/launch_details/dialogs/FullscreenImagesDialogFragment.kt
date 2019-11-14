package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.dialogs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.recyclerview.FullscreenImageAdapter
import com.ntngel1.spacexlaunches.app.utils.str
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
        setupToolbar()
        setupImagesRecyclerView()
    }

    private fun setupToolbar() {
        toolbar.title = str(R.string.imageFormat, offset + 1)
        toolbar.setNavigationOnClickListener { dismiss() }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    }

    private fun setupImagesRecyclerView() {
        imagesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        PagerSnapHelper().attachToRecyclerView(imagesRecyclerView)

        imagesRecyclerView.adapter = FullscreenImageAdapter().apply {
            images = this@FullscreenImagesDialogFragment.images
        }

        imagesRecyclerView.scrollToPosition(offset)

        val scrollListener = FullscreenImagesScrollListener { currentPosition ->
            toolbar.title = str(R.string.imageFormat, currentPosition + 1)
        }

        imagesRecyclerView.addOnScrollListener(scrollListener)
    }
}