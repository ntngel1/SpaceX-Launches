package com.ntngel1.spacexlaunches.app.ui.scenes.launches.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ntngel1.spacexlaunches.R
import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import kotlinx.android.synthetic.main.item_launch.view.*

class LaunchAdapter(
    private val onLaunchClicked: (launch: LaunchEntity) -> Unit
) : RecyclerView.Adapter<LaunchAdapter.ViewHolder>() {

    private var launches = emptyList<LaunchEntity>()

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
                launchDateTextView.text = launch.launchDate.toString()

                Glide.with(this)
                    .load(launch.links.missionPatchSmall)
                    .into(patchImageView)
            }
        }
    }
}