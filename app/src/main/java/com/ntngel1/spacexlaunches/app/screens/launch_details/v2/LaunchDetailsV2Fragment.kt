package com.ntngel1.spacexlaunches.app.screens.launch_details.v2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.common.base.BaseFragment
import com.ntngel1.spacexlaunches.app.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.screens.launch_details.LaunchDetailsView
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesDialogFragment
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.FullscreenImagesParams
import com.ntngel1.spacexlaunches.app.common.dialogs.fullscreen_images.Image
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.viewpager.LaunchDetailsFragmentAdapter
import com.ntngel1.spacexlaunches.app.utils.loadImage
import com.ntngel1.spacexlaunches.app.utils.loadImageWithPlaceholder
import com.ntngel1.spacexlaunches.app.utils.setupToolbar
import com.ntngel1.spacexlaunches.app.utils.str
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.fragment_launch_details_v2.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LaunchDetailsV2Fragment : BaseFragment(), LaunchDetailsView {

    override val layoutId: Int
        get() = R.layout.fragment_launch_details_v2

    private val args: LaunchDetailsV2FragmentArgs by navArgs()

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    @InjectPresenter
    internal lateinit var presenter: LaunchDetailsPresenter

    @ProvidePresenter
    fun provideLaunchDetailsPresenter() = App.appComponent.launchDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        presenter.flightNumber = args.flightNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_launch_details_v2.setupToolbar(
            title = str(R.string.launch_details_v2_launch),
            titleColor = Color.WHITE,
            navigationIconId = R.drawable.ic_arrow_back_white_24dp
        ) {
            findNavController().navigateUp()
        }

        setupViewPager()

        image_launch_details_v2_patch.setOnClickListener {
            presenter.onMissionPatchClicked()
        }
    }

    private fun setupViewPager() {
        pager_launch_details_v2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        pager_launch_details_v2.adapter =
            LaunchDetailsFragmentAdapter(args.flightNumber, childFragmentManager, lifecycle)

        LaunchDetailsFragmentAdapter.getTabLayoutMediator(
            tablayout_launch_details_v2,
            pager_launch_details_v2
        ).attach()
    }

    override fun showLaunchDetails(launch: LaunchEntity) {
        image_launch_details_v2_patch.loadImage(launch.links.missionPatchSmall)

        text_launch_details_v2_mission_name.text = launch.missionName
        text_launch_details_v2_launch_date.text = launch.launchDate.format(dateTimeFormatter)
        text_launch_details_v2_description.text = buildString {
            str(R.string.all_rocket_name_format, launch.rocket.rocketName).let(::appendln)
            str(R.string.all_rocket_type_format, launch.rocket.rocketType).let(::append)
        }
    }

    override fun showImageWithTitle(imageUrl: String, title: String) {
        val params = FullscreenImagesParams(
            images = listOf(Image(url = imageUrl, title = title))
        )

        FullscreenImagesDialogFragment.newInstance(params)
            .show(childFragmentManager, null)
    }

    override fun navigateBackWithLoadingError() {
        Toast.makeText(context, getString(R.string.all_unable_to_load_launch_details), Toast.LENGTH_LONG)
            .show()

        findNavController().navigateUp()
    }

    override fun setProgressBarIsVisible(isVisible: Boolean) {
        // NOT USED
    }

    override fun showImagesFullscreen(imageUrls: List<String>, offset: Int) {
        // NOT USED
    }
}