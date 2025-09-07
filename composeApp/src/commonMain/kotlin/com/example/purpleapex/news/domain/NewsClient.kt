package com.example.purpleapex.news.domain

interface NewsClient {
    suspend fun getLatest(): List<NewsArticle>
}
