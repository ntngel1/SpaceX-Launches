package com.ntngel1.spacexlaunches.app.ui.scenes.launches.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.app.App
import com.ntngel1.spacexlaunches.app.utils.loadImage
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.item_launch.view.*
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LaunchAdapter(
    private val onLaunchClicked: (launch: LaunchEntity) -> Unit
) : RecyclerView.Adapter<LaunchAdapter.ViewHolder>() {

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    private var launches = emptyList<LaunchEntity>()

    init {
        App.appComponent.inject(this)
    }

    override fun getItemCount(): Int = launches.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)
            .let(::ViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(launches[position])
    }

    fun setLaunches(newLaunches: List<LaunchEntity>) {
        val newLaunchesCopy = ArrayList<LaunchEntity>(newLaunches.size)
        newLaunchesCopy.addAll(newLaunches)

        val diffResult = LaunchDiffUtilCallback(launches, newLaunchesCopy)
            .let(DiffUtil::calculateDiff)

        launches = newLaunchesCopy
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var launch: LaunchEntity

        init {
            itemView.setOnClickListener {
                onLaunchClicked.invoke(launch)
            }
        }

        fun bind(launch: LaunchEntity) {
            this.launch = launch

            with(itemView) {
                nameTextView.text = launch.missionName
                launchDateTextView.text = getFormattedLaunchDate()

                if (launch.links.missionPatchSmall != null) {
                    patchImageView.loadImage(launch.links.missionPatchSmall)
                } else {
                    patchImageView.setImageResource(R.drawable.placeholder_no_image_available)
                }
            }
        }

        private fun getFormattedLaunchDate(): String {
            return launch.launchDate.format(dateTimeFormatter)
        }
    }
}