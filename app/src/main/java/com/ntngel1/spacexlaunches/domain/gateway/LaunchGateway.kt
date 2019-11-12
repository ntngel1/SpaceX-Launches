package com.ntngel1.spacexlaunches.domain.gateway

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import io.reactivex.Single

interface LaunchGateway {
    fun getLaunches(offset: Int, limit: Int): Single<List<LaunchEntity>>
}