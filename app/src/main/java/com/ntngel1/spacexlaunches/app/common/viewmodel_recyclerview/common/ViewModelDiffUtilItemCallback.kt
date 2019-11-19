package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common

import androidx.recyclerview.widget.DiffUtil

class ViewModelDiffUtilItemCallback(
    private val differProvider: DifferProvider
) : DiffUtil.ItemCallback<ViewModel>() {

    override fun areItemsTheSame(oldItem: ViewModel, newItem: ViewModel): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return differProvider.provideDiffer(oldItem)
            .areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: ViewModel, newItem: ViewModel): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return differProvider.provideDiffer(oldItem)
            .areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: ViewModel, newItem: ViewModel): Any? {
        return differProvider.provideDiffer(oldItem)
            .getChangePayload(oldItem, newItem)
    }
}