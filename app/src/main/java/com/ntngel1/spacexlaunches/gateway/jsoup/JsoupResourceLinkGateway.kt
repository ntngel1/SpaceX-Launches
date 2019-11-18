package com.ntngel1.spacexlaunches.gateway.jsoup

import com.ntngel1.spacexlaunches.domain.entity.ResourceLinkEntity
import com.ntngel1.spacexlaunches.domain.gateway.ResourceLinkGateway
import io.reactivex.Single
import org.jsoup.Jsoup

class JsoupResourceLinkGateway : ResourceLinkGateway {

    override fun getResourceLink(url: String) = Single.create<ResourceLinkEntity> { emitter ->
        performMetaTagParsing(url)
            .let(emitter::onSuccess)
    }

    private fun performMetaTagParsing(url: String): ResourceLinkEntity {
        var title: String? = null
        var description: String? = null
        var previewImageUrl: String? = null

        Jsoup.connect(url)
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

        return ResourceLinkEntity(
            title = title,
            description = description,
            previewImageUrl = previewImageUrl,
            url = url
        )
    }

    companion object {
        // Meta Tags
        private const val TITLE = "title"
        private const val OG_TITLE = "og:title"

        private const val DESCRIPTION = "description"
        private const val OG_DESCRIPTION = "og:description"

        private const val OG_IMAGE = "og:image"
    }
}