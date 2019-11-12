package com.ntngel1.spacexlaunches.app.di

import com.ntngel1.spacexlaunches.app.ui.launch_details.LaunchDetailsPresenter
import com.ntngel1.spacexlaunches.app.ui.main.MainActivity
import com.ntngel1.spacexlaunches.app.ui.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
    fun provideMainPresenter(): MainPresenter
    fun provideLaunchesPresenter(): LaunchDetailsPresenter
    fun provideLaunchDetailsPresenter(): LaunchDetailsPresenter
}