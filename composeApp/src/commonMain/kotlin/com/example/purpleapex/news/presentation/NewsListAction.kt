package com.example.purpleapex.news.presentation

import com.example.purpleapex.news.domain.NewsArticle

sealed interface NewsListAction {
    data class OnArticleClick(val article: NewsArticle) : NewsListAction
    data object OnArticleClose : NewsListAction
    data object OnRetryClick : NewsListAction
}
