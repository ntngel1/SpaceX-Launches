package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.common.recyclerview.GridMarginItemDecoration
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesDialogFragment
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesParams
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.Image
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview.ImageAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.dp
import kotlinx.android.synthetic.main.fragment_launch_details_images.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchDetailsImagesFragment : MvpAppCompatFragment(), LaunchDetailsImagesView {

    private val flightNumber: Int by argument(FLIGHT_NUMBER_KEY)

    private val imageAdapter =
        ImageAdapter(
            ::onImageClicked
        )

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsImagesPresenter

    @ProvidePresenter
    fun provideLaunchDetailsImagesPresenter() = App.appComponent.launchDetailsImagesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.flightNumber = flightNumber
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_launch_details_images, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImagesRecyclerView()
    }

    override fun setImages(imageUrls: List<String>) {
        imageAdapter.imageUrls = imageUrls
    }

    override fun showImagesFullscreen(imageUrls: List<String>, offset: Int) {
        val params = FullscreenImagesParams(
            images = imageUrls.map { imageUrl -> Image(url = imageUrl) },
            offset = offset
        )

        FullscreenImagesDialogFragment.newInstance(params)
            .show(childFragmentManager, null)
    }

    private fun setupImagesRecyclerView() {
        with(recycler_launch_details_images) {
            adapter = imageAdapter

            val marginItemDecoration = GridMarginItemDecoration(
                topMargin = 4.dp,
                edgeMargin = 4.dp,
                betweenElementsHorizontalMargin = 4.dp,
                betweenElementsVerticalMargin = 4.dp
            )

            addItemDecoration(marginItemDecoration)
        }
    }

    private fun onImageClicked(position: Int) {
        presenter.onImageClicked(position)
    }

    companion object {
        private const val FLIGHT_NUMBER_KEY = "flight_number"

        fun newInstance(flightNumber: Int) = LaunchDetailsImagesFragment().apply {
            arguments = bundleOf(FLIGHT_NUMBER_KEY to flightNumber)
        }
    }
}