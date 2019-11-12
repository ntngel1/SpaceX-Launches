package com.ntngel1.spacexlaunches.app.di

import com.ntngel1.spacexlaunches.app.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
}