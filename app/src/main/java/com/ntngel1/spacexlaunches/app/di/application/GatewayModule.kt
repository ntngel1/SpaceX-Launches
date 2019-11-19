package com.ntngel1.spacexlaunches.app.di.application

import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import com.ntngel1.spacexlaunches.domain.gateway.ResourceLinkGateway
import com.ntngel1.spacexlaunches.gateway.jsoup.JsoupResourceLinkGateway
import com.ntngel1.spacexlaunches.gateway.retrofit.RetrofitLaunchGateway
import com.ntngel1.spacexlaunches.gateway.retrofit.SpaceXApi
import dagger.Module
import dagger.Provides

@Module(includes = [RetrofitModule::class])
class GatewayModule {

    @Provides
    @AppScope
    fun provideLaunchGateway(spaceXApi: SpaceXApi): LaunchGateway =
        RetrofitLaunchGateway(spaceXApi)

    @Provides
    @AppScope
    fun provideResourceLinkGateway(): ResourceLinkGateway = JsoupResourceLinkGateway()
}