package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common

interface DifferProvider {
    fun provideDiffer(viewModel: ViewModel): ViewModelDiffer<ViewModel, *>
}
