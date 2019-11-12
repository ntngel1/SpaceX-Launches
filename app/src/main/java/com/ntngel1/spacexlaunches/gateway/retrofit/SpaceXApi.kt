package com.ntngel1.spacexlaunches.gateway.retrofit

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import io.reactivex.Single
import retrofit2.http.GET

interface SpaceXApi {
    @GET("launches")
    fun getLaunches(offset: Int, limit: Int): Single<List<LaunchEntity>>
}