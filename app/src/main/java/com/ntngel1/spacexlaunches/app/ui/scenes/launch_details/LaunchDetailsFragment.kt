package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.dialogs.FullscreenImagesDialogFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.viewpager.SmallImagesPagerAdapter
import com.ntngel1.spacexlaunches.app.utils.loadImage
import com.ntngel1.spacexlaunches.app.utils.buildHtmlLinks
import com.ntngel1.spacexlaunches.app.utils.setIsVisible
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launch_details.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LaunchDetailsFragment : MvpAppCompatFragment(), LaunchDetailsView {

    private val args: LaunchDetailsFragmentArgs by navArgs()

    private val imagesAdapter = SmallImagesPagerAdapter(::onFlickrImageClicked)

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
        return inflater.inflate(R.layout.fragment_launch_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImagesViewPager()
    }

    private fun setupImagesViewPager() {
        imagesViewPager.adapter = imagesAdapter
    }

    override fun setProgressBarIsVisible(isVisible: Boolean) {
        progressBar.setIsVisible(isVisible)
    }

    override fun showLaunchDetails(launch: LaunchEntity) {
        showMissionPatch(launch)
        showMissionNameAndLaunchDate(launch)
        showDescription(launch)
        showImages(launch)
        showLinks(launch)
    }

    override fun showImagesFullscreen(images: List<String>, offset: Int) {
        FullscreenImagesDialogFragment(images, offset) { position ->
            imagesViewPager.setCurrentItem(position, false)
        }.show(childFragmentManager, null)
    }

    override fun navigateBackWithLoadingError() {
        Toast.makeText(context, getString(R.string.unableToLoadLaunchDetails), Toast.LENGTH_LONG)
            .show()

        findNavController().navigateUp()
    }

    private fun onFlickrImageClicked(position: Int) {
        presenter.onFlickrImageClicked(position)
    }

    private fun showMissionNameAndLaunchDate(launch: LaunchEntity) {
        nameTextView.text = launch.missionName
        nameTextView.setIsVisible(true)

        launchDateTextView.text = launch.launchDate.format(dateTimeFormatter)
        launchDateTextView.setIsVisible(true)
    }

    private fun showMissionPatch(launch: LaunchEntity) {
        if (launch.links.missionPatch != null) {
            patchImageView.loadImage(launch.links.missionPatch)
        } else {
            patchImageView.setImageResource(R.drawable.placeholder_no_image_available)
        }

        patchImageView.setIsVisible(true)
    }

    private fun showDescription(launch: LaunchEntity) {
        val description = buildString {
            appendln(resources.getString(R.string.rocketNameFormat, launch.rocket.rocketName))
            append(resources.getString(R.string.rocketTypeFormat, launch.rocket.rocketType))
        }

        descriptionTextView.text = description
        descriptionTextView.setIsVisible(true)
    }

    private fun showImages(launch: LaunchEntity) {
        if (launch.links.flickrImages.isEmpty()) {
            return
        }

        imagesAdapter.setImages(launch.links.flickrImages)
        imagesViewPager.setIsVisible(true)
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

        if (linksText.isNotBlank()) {
            linksTextView.text = Html.fromHtml(linksText)
            linksTextView.movementMethod = LinkMovementMethod.getInstance()
            linksTextView.setIsVisible(true)
        }
    }
}