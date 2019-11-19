package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var viewModel: ViewModel? = null

    var viewType = -1
        get() {
            if (field == -1) {
                throw RuntimeException("Looks like viewType of ViewHolder is uninitialized! Please do not use viewType = -1")
            }

            return field
        }
}
