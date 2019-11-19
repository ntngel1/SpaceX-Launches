package com.ntngel1.spacexlaunches.app.screens.launch_details.v1

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.common.recyclerview.CarouselMarginItemDecoration
import com.ntngel1.spacexlaunches.app.common.recyclerview.StartSnapHelper
import com.ntngel1.spacexlaunches.app.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.screens.launch_details.LaunchDetailsView
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesDialogFragment
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesParams
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.Image
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.recyclerview.ImageCardAdapter
import com.ntngel1.spacexlaunches.app.utils.*
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launch_details_v1.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LaunchDetailsV1Fragment : MvpAppCompatFragment(),
    LaunchDetailsView {

    private val args: LaunchDetailsV1FragmentArgs by navArgs()

    private val imageCardAdapter =
        ImageCardAdapter(
            ::onFlickrImageClicked
        )

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsPresenter

    @ProvidePresenter
    fun provideLaunchesPresenter() = App.appComponent.provideLaunchDetailsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        presenter.launchId = args.launchId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_details_v1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image_launch_patch.setOnClickListener {
            presenter.onMissionPatchClicked()
        }

        toolbar.setupToolbar(navigationIconId = R.drawable.ic_arrow_back_white_24dp) {
            findNavController().navigateUp()
        }

        setupImagesRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setTranslucentStatusBar(true)
    }

    override fun onPause() {
        super.onPause()
        setTranslucentStatusBar(false)
    }

    override fun setProgressBarIsVisible(isVisible: Boolean) {
        progress_bar_launch_details.setVisibleOrGone(isVisible)
    }

    override fun showLaunchDetails(launch: LaunchEntity) {
        showMissionPatch(launch)
        showMissionNameAndLaunchDate(launch)
        showDescription(launch)
        showImages(launch)
        showLinks(launch)
    }

    override fun showImagesFullscreen(imageUrls: List<String>, offset: Int) {
        val params = FullscreenImagesParams(
            images = imageUrls.map { imageUrl -> Image(url = imageUrl) },
            offset = offset
        )

        FullscreenImagesDialogFragment.newInstance(params)
            .show(childFragmentManager, null)
    }

    override fun showImageWithTitle(imageUrl: String, title: String) {
        val params = FullscreenImagesParams(
            images = listOf(Image(url = imageUrl, title = title))
        )

        FullscreenImagesDialogFragment.newInstance(params)
            .show(childFragmentManager, null)
    }

    override fun navigateBackWithLoadingError() {
        Toast.makeText(context, getString(R.string.unableToLoadLaunchDetails), Toast.LENGTH_LONG)
            .show()

        findNavController().navigateUp()
    }

    private fun setupImagesRecyclerView() {
        imagesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        imagesRecyclerView.adapter = imageCardAdapter
        imagesRecyclerView.addItemDecoration(CarouselMarginItemDecoration())

        StartSnapHelper().attachToRecyclerView(imagesRecyclerView)
    }

    private fun onFlickrImageClicked(position: Int) {
        presenter.onFlickrImageClicked(position)
    }

    private fun showMissionNameAndLaunchDate(launch: LaunchEntity) {
        toolbar.title = launch.missionName

        text_launch_item_launch_date.text = launch.launchDate.format(dateTimeFormatter)
        text_launch_item_launch_date.setVisibleOrGone(true)
    }

    private fun showMissionPatch(launch: LaunchEntity) {
        if (launch.links.missionPatch != null) {
            image_launch_patch.loadImage(launch.links.missionPatch)
        } else {
            image_launch_patch.setImageResource(R.drawable.placeholder_no_image_available)
        }

        image_launch_patch.setVisibleOrGone(true)
    }

    private fun showDescription(launch: LaunchEntity) {
        val description = buildString {
            appendln(resources.getString(R.string.rocketNameFormat, launch.rocket.rocketName))
            append(resources.getString(R.string.rocketTypeFormat, launch.rocket.rocketType))
        }

        descriptionTextView.text = description
        descriptionTextView.setVisibleOrGone(true)
    }

    private fun showImages(launch: LaunchEntity) {
        val hasImages = launch.links.flickrImages.isNotEmpty()

        if (hasImages) {
            imageCardAdapter.images = launch.links.flickrImages
        }

        imagesRecyclerView.setVisibleOrGone(hasImages)
        text_images.setVisibleOrGone(hasImages)
    }

    private fun showLinks(launch: LaunchEntity) {
        val linksText = buildHtmlLinks(
            listOf(
                launch.links.redditMedia to resources.getString(R.string.redditMedia),
                launch.links.article to resources.getString(R.string.article),
                launch.links.wikipedia to resources.getString(R.string.wikipedia),
                launch.links.youtube to resources.getString(R.string.youtube)
            )
        )

        val hasLinks = linksText.isNotBlank()

        if (hasLinks) {
            linksTextView.text = Html.fromHtml(linksText)
            linksTextView.movementMethod = LinkMovementMethod.getInstance()
        }

        text_resources.setVisibleOrGone(hasLinks)
        linksTextView.setVisibleOrGone(hasLinks)
    }
}