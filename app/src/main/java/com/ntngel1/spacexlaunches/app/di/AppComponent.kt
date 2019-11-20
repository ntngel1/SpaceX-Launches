package com.ntngel1.spacexlaunches.app.di

import com.ntngel1.spacexlaunches.app.screens.launch_details.v1.LaunchDetailsV1Fragment
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.LaunchDetailsV2Fragment
import com.ntngel1.spacexlaunches.app.screens.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.images.LaunchDetailsImagesPresenter
import com.ntngel1.spacexlaunches.app.screens.launch_details.v2.resources.LaunchDetailsResourcesPresenter
import com.ntngel1.spacexlaunches.app.screens.launches.LaunchesFragment
import com.ntngel1.spacexlaunches.app.screens.launches.LaunchesPresenter
import com.ntngel1.spacexlaunches.app.screens.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class, DateTimeModule::class])
interface AppComponent {
    fun inject(target: LaunchDetailsV1Fragment)
    fun inject(target: LaunchDetailsV2Fragment)
    fun inject(target: LaunchesFragment)

    val mainPresenter: MainPresenter
    val launchesPresenter: LaunchesPresenter
    val launchDetailsPresenter: LaunchDetailsPresenter
    val launchDetailsResourcesPresenter: LaunchDetailsResourcesPresenter
    val launchDetailsImagesPresenter: LaunchDetailsImagesPresenter
}