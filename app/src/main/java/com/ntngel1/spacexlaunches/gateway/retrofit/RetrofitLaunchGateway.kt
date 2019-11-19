package com.ntngel1.spacexlaunches.gateway.retrofit

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.Single

class RetrofitLaunchGateway(private val spaceXApi: SpaceXApi) : LaunchGateway {

    override fun getLaunchesWithDescendingLaunchDate(offset: Int, limit: Int) =
        spaceXApi.getLaunchesWithDescendingLaunchDate(offset, limit)


    override fun getLaunchByFlightNumber(flightNumber: Int) =
        spaceXApi.getLaunchByFlightNumber(flightNumber)

}