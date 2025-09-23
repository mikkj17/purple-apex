package com.example.purpleapex.di

import com.apollographql.apollo.ApolloClient
import com.example.purpleapex.circuit.data.network.ApolloCircuitClient
import com.example.purpleapex.circuit.data.repository.DefaultCircuitRepository
import com.example.purpleapex.circuit.domain.CircuitClient
import com.example.purpleapex.circuit.domain.CircuitRepository
import com.example.purpleapex.circuit.presentation.circuit_detail.CircuitDetailViewModel
import com.example.purpleapex.constructor.data.network.ApolloConstructorClient
import com.example.purpleapex.constructor.data.repository.DefaultConstructorRepository
import com.example.purpleapex.constructor.domain.ConstructorClient
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.constructor.presentation.constructor_detail.ConstructorDetailViewModel
import com.example.purpleapex.core.util.Constants
import com.example.purpleapex.driver.data.network.ApolloDriverClient
import com.example.purpleapex.driver.data.repository.DefaultDriverRepository
import com.example.purpleapex.driver.domain.DriverClient
import com.example.purpleapex.driver.domain.DriverRepository
import com.example.purpleapex.driver.presentation.driver_detail.DriverDetailViewModel
import com.example.purpleapex.news.data.network.KtorNewsClient
import com.example.purpleapex.news.data.repository.DefaultNewsRepository
import com.example.purpleapex.news.domain.NewsClient
import com.example.purpleapex.news.domain.NewsRepository
import com.example.purpleapex.news.presentation.NewsListViewModel
import com.example.purpleapex.qualifying.data.network.ApolloQualifyingClient
import com.example.purpleapex.qualifying.data.repository.DefaultQualifyingRepository
import com.example.purpleapex.qualifying.domain.QualifyingClient
import com.example.purpleapex.qualifying.domain.QualifyingRepository
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
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .build()
    }

    single {
        HttpClient(get<HttpClientEngine>()) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
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

    singleOf(::ApolloQualifyingClient).bind<QualifyingClient>()
    singleOf(::DefaultQualifyingRepository).bind<QualifyingRepository>()

    singleOf(::KtorNewsClient).bind<NewsClient>()
    singleOf(::DefaultNewsRepository).bind<NewsRepository>()

    viewModelOf(::StandingsListViewModel)
    viewModelOf(::RaceListViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::DriverDetailViewModel)
    viewModelOf(::ConstructorDetailViewModel)
    viewModelOf(::CircuitDetailViewModel)
    viewModelOf(::NewsListViewModel)
}
