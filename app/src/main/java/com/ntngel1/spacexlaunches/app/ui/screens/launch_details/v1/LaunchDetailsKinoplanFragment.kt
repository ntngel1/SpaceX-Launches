package com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.LaunchDetailsView
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.dialogs.fullscreen_images.FullscreenImagesDialogFragment
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.dialogs.fullscreen_images.FullscreenImagesParams
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.dialogs.fullscreen_images.Image
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.viewpager.LaunchDetailsFragmentAdapter
import com.ntngel1.spacexlaunches.app.utils.loadImage
import com.ntngel1.spacexlaunches.app.utils.setupToolbar
import com.ntngel1.spacexlaunches.app.utils.str
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launch_details_kinoplan.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LaunchDetailsKinoplanFragment : MvpAppCompatFragment(),
    LaunchDetailsView {

    private val args: LaunchDetailsKinoplanFragmentArgs by navArgs()

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsPresenter

    @ProvidePresenter
    fun provideLaunchDetailsPresenter() = App.appComponent.provideLaunchDetailsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        presenter.launchId = args.launchId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_launch_details_kinoplan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupToolbar(
            title = str(R.string.launch),
            titleColor = Color.WHITE,
            navigationIconId = R.drawable.ic_arrow_back_white_24dp
        ) {
            findNavController().navigateUp()
        }

        setupViewPager()

        image_mission_patch.setOnClickListener {
            presenter.onMissionPatchClicked()
        }
    }

    private fun setupViewPager() {
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pager.adapter = LaunchDetailsFragmentAdapter(args.launchId, childFragmentManager, lifecycle)
        LaunchDetailsFragmentAdapter.getTabLayoutMediator(tab_layout, pager).attach()
    }

    override fun showLaunchDetails(launch: LaunchEntity) {
        image_mission_patch.loadImage(launch.links.missionPatchSmall, placeholder = null)

        text_mission_name.text = launch.missionName
        text_launch_date.text = launch.launchDate.format(dateTimeFormatter)
        text_description.text = buildString {
            str(R.string.rocketNameFormat, launch.rocket.rocketName).let(::appendln)
            str(R.string.rocketTypeFormat, launch.rocket.rocketType).let(::append)
        }
    }

    override fun showImagesFullscreen(imageUrls: List<String>, offset: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showImageWithTitle(imageUrl: String, title: String) {
        val params = FullscreenImagesParams(
            images = listOf(Image(url = imageUrl, title = title))
        )

        FullscreenImagesDialogFragment.newInstance(params)
            .show(childFragmentManager, null)
    }

    override fun setProgressBarIsVisible(isVisible: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateBackWithLoadingError() {
        Toast.makeText(context, getString(R.string.unableToLoadLaunchDetails), Toast.LENGTH_LONG)
            .show()

        findNavController().navigateUp()
    }
}