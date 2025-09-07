package com.example.purpleapex.news.domain

interface NewsRepository {
    suspend fun getLatest(): List<NewsArticle>
}
