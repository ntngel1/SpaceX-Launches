package com.ntngel1.spacexlaunches.app.di.app

import com.ntngel1.spacexlaunches.app.di.launch_details.v2.LaunchDetailsV2Component
import com.ntngel1.spacexlaunches.app.di.launches.LaunchesComponent
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.LaunchDetailsKinoplanFragment
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.images.LaunchDetailsImagesPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launch_details.v1.resources.LaunchDetailsResourcesPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.launches.LaunchesPresenter
import com.ntngel1.spacexlaunches.app.ui.screens.main.MainPresenter
import dagger.Component

@AppScope
@Component(modules = [GatewayModule::class, DateTimeModule::class])
interface AppComponent {
    fun inject(target: LaunchDetailsKinoplanFragment)

    fun provideMainPresenter(): MainPresenter
    fun provideLaunchesPresenter(): LaunchesPresenter
    fun provideLaunchDetailsPresenter(): LaunchDetailsPresenter
    fun provideLaunchDetailsResourcesPresenter(): LaunchDetailsResourcesPresenter
    fun provideLaunchDetailsImagesPresenter(): LaunchDetailsImagesPresenter

    val launchesComponentBuilder: LaunchesComponent.Builder
    val launchDetailsV2ComponentBuilder: LaunchDetailsV2Component.Builder
}