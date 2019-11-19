package com.ntngel1.spacexlaunches.app

import android.app.Application
import com.ntngel1.spacexlaunches.app.di.application.AppComponent
import com.ntngel1.spacexlaunches.app.di.application.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}