package com.example.purpleapex.di

import com.apollographql.apollo.ApolloClient
import com.example.purpleapex.circuit.data.network.ApolloCircuitClient
import com.example.purpleapex.circuit.data.repository.DefaultCircuitRepository
import com.example.purpleapex.circuit.domain.CircuitClient
import com.example.purpleapex.circuit.domain.CircuitRepository
import com.example.purpleapex.constructor.data.network.ApolloConstructorClient
import com.example.purpleapex.constructor.data.repository.DefaultConstructorRepository
import com.example.purpleapex.constructor.domain.ConstructorClient
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.core.util.Constants
import com.example.purpleapex.driver.data.network.ApolloDriverClient
import com.example.purpleapex.driver.data.repository.DefaultDriverRepository
import com.example.purpleapex.driver.domain.DriverClient
import com.example.purpleapex.driver.domain.DriverRepository
import com.example.purpleapex.race.data.network.ApolloRaceClient
import com.example.purpleapex.race.data.repository.DefaultRaceRepository
import com.example.purpleapex.race.domain.RaceClient
import com.example.purpleapex.race.domain.RaceRepository
import com.example.purpleapex.race.presentation.race_list.RaceListViewModel
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

    singleOf(::ApolloCircuitClient).bind<CircuitClient>()
    singleOf(::DefaultCircuitRepository).bind<CircuitRepository>()

    singleOf(::ApolloStandingsClient).bind<StandingsClient>()
    singleOf(::DefaultStandingsRepository).bind<StandingsRepository>()

    singleOf(::ApolloRaceClient).bind<RaceClient>()
    singleOf(::DefaultRaceRepository).bind<RaceRepository>()

    viewModelOf(::StandingsListViewModel)
    viewModelOf(::RaceListViewModel)
    viewModelOf(::SearchViewModel)
}
