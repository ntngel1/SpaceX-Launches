package com.ntngel1.spacexlaunches.app.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * @param links пары ссылка -> название, к примеру: "https://www.google.com" to "Google"
 *              Null-ссылки игнорируются
 */
fun buildHtmlLinks(links: List<Pair<String?, String>>) =
    links.filter { (link, _) -> link != null }
        .joinToString("<br>") { (link, name) ->
            """<a href="$link">$name</a>"""
        }

fun String.fromHtml(): Spanned =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
        Html.fromHtml(this)
    else
        Html.fromHtml(this, 0)

