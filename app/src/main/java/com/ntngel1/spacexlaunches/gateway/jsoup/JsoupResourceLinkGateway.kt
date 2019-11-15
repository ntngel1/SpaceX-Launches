package com.ntngel1.spacexlaunches.gateway.jsoup

import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import com.ntngel1.spacexlaunches.domain.gateway.ResourceLinkGateway
import io.reactivex.Single
import org.jsoup.Jsoup

class JsoupResourceLinkGateway : ResourceLinkGateway {

    override fun getResourceLink(url: String) = Single.create<ResourceLinkEntity> { emitter ->
        var title: String? = null
        var description: String? = null
        var previewImageUrl: String? = null

        val html = Jsoup.connect(url)
            .execute()
            .body()
            .let(Jsoup::parse)
            .getElementsByTag("meta")
            .forEach { element ->
                val property = element.attr("property")
                val content = element.attr("content")

                when (property) {
                    TITLE, OG_TITLE -> title = content
                    DESCRIPTION, OG_DESCRIPTION -> description = content
                    OG_IMAGE -> previewImageUrl = content
                }
            }

        val resourceLink = ResourceLinkEntity(
            title = title,
            description = description,
            previewImageUrl = previewImageUrl,
            url = url
        )

        emitter.onSuccess(resourceLink)
    }

    companion object {
        private const val TITLE = "title"
        private const val OG_TITLE = "og:title"

        private const val DESCRIPTION = "description"
        private const val OG_DESCRIPTION = "og:description"

        private const val OG_IMAGE = "og:image"
    }
}