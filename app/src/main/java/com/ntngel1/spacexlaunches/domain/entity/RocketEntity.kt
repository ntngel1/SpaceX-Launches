package com.ntngel1.spacexlaunches.domain.entity

import com.google.gson.annotations.SerializedName

data class RocketEntity(

    @SerializedName("rocket_name")
    val rocketName: String,

    @SerializedName("rocket_type")
    val rocketType: String
)