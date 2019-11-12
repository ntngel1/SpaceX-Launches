package com.ntngel1.spacexlaunches.domain.entity

import com.google.gson.annotations.SerializedName

data class LaunchLinksEntity(

    @SerializedName("mission_patch")
    val missionPatch: String?,

    @SerializedName("mission_patch_small")
    val missionPatchSmall: String?,

    @SerializedName("reddit_media")
    val redditMedia: String?,

    @SerializedName("article_link")
    val article: String?,

    @SerializedName("video_link")
    val youtube: String?,

    @SerializedName("flickr_images")
    val flickrImages: List<String>,

    val wikipedia: String?
)