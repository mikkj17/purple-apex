package com.example.purpleapex.constructor.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.ConstructorsQuery
import com.example.purpleapex.constructor.data.mappers.toConstructor
import com.example.purpleapex.constructor.domain.ConstructorClient

class ApolloConstructorClient(
    private val apolloClient: ApolloClient
) : ConstructorClient {
    override suspend fun getConstructors(year: Int?, round: Int?) = apolloClient
        .query(ConstructorsQuery(Optional.presentIfNotNull(year), Optional.presentIfNotNull(round)))
        .execute()
        .dataAssertNoErrors
        .constructors
        .map { it.toConstructor() }
}
