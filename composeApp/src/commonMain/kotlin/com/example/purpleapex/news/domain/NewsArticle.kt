package com.example.purpleapex.news.domain

/**
 * Minimal representation of a news article displayed on the Home screen.
 */
data class NewsArticle(
    val id: String,
    val headline: String,
    val description: String?,
    val published: String?, // ISO-8601 or human readable string
    val imageUrl: String?,
    val linkUrl: String?
)