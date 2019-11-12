package com.ntngel1.spacexlaunches.app.utils

fun makeHtmlLinks(links: List<Pair<String?, String>>): String {
    return links.filter { (link, _) -> link != null }
        .joinToString("<br>") { (link, name) ->
            """<a href="$link">$name</a>"""
        }
}