package com.ntngel1.spacexlaunches.app.screens.launch_details.v2.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.common.base.BaseFragment
import com.ntngel1.spacexlaunches.app.common.recyclerview.GridMarginItemDecoration
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesDialogFragment
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesParams
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.Image
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview.ImageAdapter
import com.ntngel1.spacexlaunches.app.utils.argument
import com.ntngel1.spacexlaunches.app.utils.dp
import com.ntngel1.spacexlaunches.app.utils.visibleOrGone
import kotlinx.android.synthetic.main.fragment_launch_details_images.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LaunchDetailsImagesFragment : BaseFragment(), LaunchDetailsImagesView {

    override val layoutId: Int
        get() = R.layout.fragment_launch_details_images

    private val flightNumber: Int by argument(FLIGHT_NUMBER_KEY)

    private val imageAdapter = ImageAdapter(::onImageClicked)

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsImagesPresenter

    @ProvidePresenter
    fun provideLaunchDetailsImagesPresenter() = App.appComponent.launchDetailsImagesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.flightNumber = flightNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImagesRecyclerView()

        button_launch_details_images_try_again.setOnClickListener {
            presenter.onTryAgainClicked()
        }
    }

    override fun setImages(imageUrls: List<String>) {
        imageAdapter.items = imageUrls
    }

    override fun showImagesFullscreen(imageUrls: List<String>, offset: Int) {
        val params = FullscreenImagesParams(
            images = imageUrls.map { imageUrl -> Image(url = imageUrl) },
            offset = offset
        )

        FullscreenImagesDialogFragment.newInstance(params)
            .show(childFragmentManager, null)
    }

    override fun setIsLoadingError(isLoadingError: Boolean) {
        linearlayout_launch_details_images_loading_error.visibleOrGone(isLoadingError)
    }

    override fun setIsLoading(isLoading: Boolean) {
        progressbar_launch_details_images.visibleOrGone(isLoading)
    }

    override fun setIsStubVisible(isVisible: Boolean) {
        text_launch_details_images_stub.visibleOrGone(isVisible)
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