package com.example.purpleapex.news.presentation

import com.example.purpleapex.news.domain.NewsArticle

data class NewsState(
    val isLoading: Boolean = false,
    val articles: List<NewsArticle> = emptyList(),
    val error: String? = null,
)
