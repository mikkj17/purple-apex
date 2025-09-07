package com.example.purpleapex.news.presentation

import com.example.purpleapex.news.domain.NewsArticle

data class NewsListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedArticle: NewsArticle? = null,
    val articles: List<NewsArticle> = emptyList(),
)
