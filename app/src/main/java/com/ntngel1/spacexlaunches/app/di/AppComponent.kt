package com.ntngel1.spacexlaunches.app.di

import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.LaunchDetailsFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.LaunchDetailsKinoplanFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_images.LaunchDetailsImagesPresenter
import com.ntngel1.spacexlaunches.app.ui.scenes.launch_details.launch_details_resources.LaunchDetailsResourcesPresenter
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.LaunchesFragment
import com.ntngel1.spacexlaunches.app.ui.scenes.launches.LaunchesPresenter
import com.ntngel1.spacexlaunches.app.ui.scenes.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class, DateTimeModule::class])
interface AppComponent {
    fun inject(target: LaunchDetailsFragment)
    fun inject(target: LaunchDetailsKinoplanFragment)
    fun inject(target: LaunchesFragment)

    fun provideMainPresenter(): MainPresenter
    fun provideLaunchesPresenter(): LaunchesPresenter
    fun provideLaunchDetailsPresenter(): LaunchDetailsPresenter
    fun provideLaunchDetailsResourcesPresenter(): LaunchDetailsResourcesPresenter
    fun provideLaunchDetailsImagesPresenter(): LaunchDetailsImagesPresenter
}