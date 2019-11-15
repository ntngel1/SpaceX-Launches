package com.ntngel1.spacexlaunches.domain.gateway

import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import io.reactivex.Single

interface ResourceLinkGateway {
    fun getResourceLink(url: String): Single<ResourceLinkEntity>
}