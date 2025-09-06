package com.example.purpleapex.news.data.repository

import com.example.purpleapex.news.domain.NewsArticle
import com.example.purpleapex.news.domain.NewsClient
import com.example.purpleapex.news.domain.NewsRepository

class DefaultNewsRepository(
    private val client: NewsClient
) : NewsRepository {
    override suspend fun getLatest(): List<NewsArticle> = client.getLatest()
}