package com.example.purpleapex.news.data.mappers

import com.example.NewsQuery
import com.example.purpleapex.news.domain.NewsArticle

fun NewsQuery.New.toNewsArticle(): NewsArticle {
    return NewsArticle(
        id = id,
        headline = headline,
        description = description,
        published = published,
        imageUrl = images.firstOrNull()?.url,
        linkUrl = links.web
    )
}
