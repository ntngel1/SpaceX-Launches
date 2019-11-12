package com.ntngel1.spacexlaunches.app.ui.scenes.launch_details

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.utils.loadImage
import com.ntngel1.spacexlaunches.app.utils.makeHtmlLinks
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

    override fun setProgressBarIsVisible(isVisible: Boolean) {
        progressBar.setIsVisible(isVisible)
    }

    override fun showLaunchDetails(launch: LaunchEntity) {
        nameTextView.text = launch.missionName
        nameTextView.setIsVisible(true)

        launchDateTextView.text = launch.launchDate.format(dateTimeFormatter)
        launchDateTextView.setIsVisible(true)

        if (launch.links.missionPatch != null) {
            patchImageView.loadImage(launch.links.missionPatch)
        } else {
            patchImageView.setImageResource(R.drawable.no_image_available)
        }

        patchImageView.setIsVisible(true)

        val linksText = makeHtmlLinks(
            listOf(
                launch.links.redditMedia to "Reddit Media",
                launch.links.article to "Article",
                launch.links.wikipedia to "Wikipedia",
                launch.links.youtube to "YouTube"
            )
        )

        if (linksText.isNotBlank()) {
            linksTextView.text = Html.fromHtml(linksText)
            linksTextView.movementMethod = LinkMovementMethod.getInstance()
            linksTextView.setIsVisible(true)
        }
    }
}