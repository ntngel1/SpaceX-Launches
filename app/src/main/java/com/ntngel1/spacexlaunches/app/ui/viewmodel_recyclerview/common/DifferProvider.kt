package com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common

interface DifferProvider {
    fun provideDiffer(viewModel: ViewModel): ViewModelDiffer<ViewModel, *>
}
