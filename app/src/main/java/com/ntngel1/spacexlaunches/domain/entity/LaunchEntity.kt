package com.ntngel1.spacexlaunches.domain.entity

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class LaunchEntity(
    @SerializedName("mission_name")
    val missionName: String,
    val links: LaunchLinksEntity,
    @SerializedName("launch_date_local")
    val launchDate: LocalDateTime
)