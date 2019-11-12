package com.ntngel1.spacexlaunches.gateway.retrofit

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import com.ntngel1.spacexlaunches.domain.gateway.LaunchGateway
import io.reactivex.Single

class RetrofitLaunchGateway(private val spaceXApi: SpaceXApi) : LaunchGateway {

    override fun getLaunches(offset: Int, limit: Int): Single<List<LaunchEntity>> {
        return spaceXApi.getLaunchesWithDescendingLaunchDate(offset, limit)
    }
}