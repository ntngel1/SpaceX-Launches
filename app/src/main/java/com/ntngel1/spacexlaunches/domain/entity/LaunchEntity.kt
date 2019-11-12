package com.ntngel1.spacexlaunches.domain.entity

import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime

data class LaunchEntity(
    @SerializedName("flight_number")
    val flightNumber: Int,

    @SerializedName("mission_name")
    val missionName: String,

    @SerializedName("launch_date_local")
    val launchDate: ZonedDateTime,

    val links: LaunchLinksEntity
)