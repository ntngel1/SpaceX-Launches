package com.ntngel1.spacexlaunches.app.utils

/**
 * @param links пары ссылка -> название, к примеру: "https://www.google.com" to "Google"
 *              Null-ссылки игнорируются
 */
fun makeHtmlLinks(links: List<Pair<String?, String>>): String {
    return links.filter { (link, _) -> link != null }
        .joinToString("<br>") { (link, name) ->
            """<a href="$link">$name</a>"""
        }
}