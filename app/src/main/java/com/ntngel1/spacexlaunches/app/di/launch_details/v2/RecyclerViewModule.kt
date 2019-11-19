package com.ntngel1.spacexlaunches.app.di.launch_details.v2

import com.ntngel1.spacexlaunches.app.di.launch_details.v1.LaunchDetailsV1Scope
import com.ntngel1.spacexlaunches.app.ui.recyclerview.CarouselMarginItemDecoration
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.recyclerview.ImageCardAdapter
import dagger.Module
import dagger.Provides

@Module
class RecyclerViewModule(
    private val onImageClicked: (position: Int) -> Unit
) {

    @Provides
    @LaunchDetailsV1Scope
    fun provideImageCardAdapter() = ImageCardAdapter(onImageClicked)

    @Provides
    @LaunchDetailsV1Scope
    fun provideCarouselMarginItemDecoration() = CarouselMarginItemDecoration()
}