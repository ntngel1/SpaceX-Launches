package com.ntngel1.spacexlaunches.domain.gateway

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import io.reactivex.Single

interface LaunchGateway {
    fun getLaunchesWithDescendingLaunchDate(offset: Int, limit: Int): Single<List<LaunchEntity>>
    fun getLaunchByFlightNumber(flightNumber: Int): Single<LaunchEntity>
}