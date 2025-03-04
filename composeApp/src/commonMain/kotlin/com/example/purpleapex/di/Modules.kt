package com.example.purpleapex.di

import com.apollographql.apollo.ApolloClient
import com.example.purpleapex.driver.data.network.ApolloDriverClient
import com.example.purpleapex.driver.domain.network.DriverClient
import com.example.purpleapex.driver.presentation.driver_list.DriverListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl("https://purple-apex.com/graphql")
            .build()
    }
    singleOf(::ApolloDriverClient).bind<DriverClient>()

    viewModelOf(::DriverListViewModel)
}
