package com.example.purpleapex.di

import com.apollographql.apollo.ApolloClient
import com.example.purpleapex.constructor.data.network.ApolloConstructorClient
import com.example.purpleapex.constructor.data.repository.DefaultConstructorRepository
import com.example.purpleapex.constructor.domain.ConstructorClient
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.core.util.Constants
import com.example.purpleapex.driver.data.network.ApolloDriverClient
import com.example.purpleapex.driver.data.repository.DefaultDriverRepository
import com.example.purpleapex.driver.domain.DriverClient
import com.example.purpleapex.driver.domain.DriverRepository
import com.example.purpleapex.search.presentation.SearchViewModel
import com.example.purpleapex.standings.data.network.ApolloStandingsClient
import com.example.purpleapex.standings.data.repository.DefaultStandingsRepository
import com.example.purpleapex.standings.domain.StandingsClient
import com.example.purpleapex.standings.domain.StandingsRepository
import com.example.purpleapex.standings.presentation.standings_list.StandingsListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .build()
    }
    singleOf(::ApolloDriverClient).bind<DriverClient>()
    singleOf(::DefaultDriverRepository).bind<DriverRepository>()

    singleOf(::ApolloConstructorClient).bind<ConstructorClient>()
    singleOf(::DefaultConstructorRepository).bind<ConstructorRepository>()

    singleOf(::ApolloStandingsClient).bind<StandingsClient>()
    singleOf(::DefaultStandingsRepository).bind<StandingsRepository>()
    viewModelOf(::StandingsListViewModel)
    viewModelOf(::SearchViewModel)
}
