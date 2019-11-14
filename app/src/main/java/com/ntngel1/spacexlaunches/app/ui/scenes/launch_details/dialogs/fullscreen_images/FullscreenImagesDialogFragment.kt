package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.dialogs.fullscreen_images

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.recyclerview.FullscreenImageAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.str
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.dialog_fullscreen_images.*

class FullscreenImagesDialogFragment : DialogFragment() {

    sealed class Params {
        @Parcelize
        data class Images(
            val images: List<String>,
            val offset: Int = 0
        ) : Params(), Parcelable

        @Parcelize
        data class Image(
            val image: String,
            val title: String = ""
        ) : Params(), Parcelable
    }

    private val params by argument<Params>(PARAMS_KEY)

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
        params.let { params ->
            toolbar.title = when (params) {
                is Params.Images -> str(R.string.imageFormat, params.offset + 1)
                is Params.Image -> params.title
            }
        }

        toolbar.setNavigationOnClickListener { dismiss() }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    }


    private fun setupImagesRecyclerView() {
        imagesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        PagerSnapHelper().attachToRecyclerView(imagesRecyclerView)

        val adapter = FullscreenImageAdapter()

        params.let { params ->
            when (params) {
                is Params.Images -> {
                    initRecyclerViewWithImages(params)
                }
                is Params.Image -> {
                    initRecyclerViewWithImage(params)
                }
            }
        }

        imagesRecyclerView.adapter = adapter
    }

    private fun initRecyclerViewWithImage(params: Params.Image) {
        val adapter = FullscreenImageAdapter()
        adapter.images = listOf(params.image)
        imagesRecyclerView.adapter = adapter
    }

    private fun initRecyclerViewWithImages(params: Params.Images) {
        val adapter = FullscreenImageAdapter()

        adapter.images = params.images
        imagesRecyclerView.scrollToPosition(params.offset)

        val scrollListener = FullscreenImagesScrollListener { currentPosition ->
            toolbar.title = str(R.string.imageFormat, currentPosition + 1)
        }

        imagesRecyclerView.addOnScrollListener(scrollListener)
        imagesRecyclerView.adapter = adapter
    }

    companion object {
        private const val PARAMS_KEY = "params"

        fun newInstance(params: Params) = FullscreenImagesDialogFragment().apply {
            arguments = bundleOf(PARAMS_KEY to params)
        }
    }
}