package com.ntngel1.spacexlaunches.gateway.retrofit

import com.ntngel1.spacexlaunches.domain.entity.LaunchEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceXApi {

    @GET("launches")
    fun getLaunchesWithDescendingLaunchDate(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String = "launch_date_local",
        @Query("order") order: String = "desc"
    ): Single<List<LaunchEntity>>
}