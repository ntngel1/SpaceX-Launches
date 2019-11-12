package com.ntngel1.spacexlaunches.domain.entity

import com.google.gson.annotations.SerializedName

data class LaunchLinksEntity(
    @SerializedName("mission_patch")
    val missionPatch: String?,
    @SerializedName("mission_patch_small")
    val missionPatchSmall: String?
)