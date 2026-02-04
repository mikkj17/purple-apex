package com.example.purpleapex.news.data.network

import com.apollographql.apollo.ApolloClient
import com.example.NewsQuery
import com.example.purpleapex.news.data.mappers.toNewsArticle
import com.example.purpleapex.news.domain.NewsArticle
import com.example.purpleapex.news.domain.NewsClient

class ApolloNewsClient(
    private val apolloClient: ApolloClient
) : NewsClient {
    override suspend fun getLatest(): List<NewsArticle> {
        return apolloClient
            .query(NewsQuery())
            .execute()
            .dataAssertNoErrors
            .news
            .map { it.toNewsArticle() }
    }
}
