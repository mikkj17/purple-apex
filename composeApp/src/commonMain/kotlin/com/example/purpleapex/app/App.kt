package com.example.purpleapex.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.purpleapex.circuit.presentation.circuit_detail.CircuitDetailScreenRoot
import com.example.purpleapex.constructor.presentation.constructor_detail.ConstructorDetailScreenRoot
import com.example.purpleapex.driver.presentation.driver_detail.DriverDetailScreenRoot
import com.example.purpleapex.home.presentation.HomeScreenRoot
import com.example.purpleapex.qualifying.presentation.qualifying_detail.QualifyingDetailScreenRoot
import com.example.purpleapex.race.presentation.race_detail.LapTimesScreenRoot
import com.example.purpleapex.race.presentation.race_detail.PitStopsScreenRoot
import com.example.purpleapex.race.presentation.race_detail.RaceDetailScreenRoot
import com.example.purpleapex.race.presentation.race_list.RaceListScreenRoot
import com.example.purpleapex.search.presentation.SearchScreenRoot
import com.example.purpleapex.standings.presentation.standings_list.StandingsListScreenRoot
import com.example.purpleapex.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = { graph, root, reselected ->
                        if (reselected) {
                            // On reselect: pop back to the tab's root leaf if it's in the back stack.
                            val popped = navController.popBackStack(root, inclusive = false)
                            if (!popped) {
                                // If the root isn't present yet, navigate to the tab graph to restore state.
                                navController.navigate(graph) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo<Route.Graph> { saveState = true }
                                }
                            }
                        } else {
                            // Switching tabs: navigate to the tab graph to restore its saved inner back stack.
                            navController.navigate(graph) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo<Route.Graph> { saveState = true }
                            }
                        }
                    },
                )
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.Graph,
                modifier = Modifier.padding(innerPadding)
            ) {
                navigation<Route.Graph>(
                    startDestination = Route.HomeGraph
                ) {
                    // Home tab graph
                    navigation<Route.HomeGraph>(startDestination = Route.Home) {
                        composable<Route.Home> {
                            HomeScreenRoot()
                        }
                    }

                    // Standings tab graph
                    navigation<Route.StandingsGraph>(startDestination = Route.Standings) {
                        composable<Route.Standings> {
                            StandingsListScreenRoot()
                        }
                    }

                    // Racing tab graph and its sub-screens
                    navigation<Route.RacingGraph>(startDestination = Route.Racing) {
                        composable<Route.Racing> {
                            RaceListScreenRoot(
                                onRaceClick = { race ->
                                    navController.navigate(
                                        Route.RaceDetail(
                                            season = race.season,
                                            round = race.round,
                                        )
                                    )
                                }
                            )
                        }
                        composable<Route.RaceDetail> {
                            RaceDetailScreenRoot(
                                onBackClick = { navController.navigateUp() },
                                onQualifyingClick = { season, round ->
                                    navController.navigate(Route.QualifyingDetail(season, round))
                                },
                                onLapTimesClick = { season, round ->
                                    navController.navigate(Route.LapTimes(season, round))
                                },
                                onPitStopsClick = { season, round ->
                                    navController.navigate(Route.PitStops(season, round))
                                },
                            )
                        }
                        composable<Route.QualifyingDetail> {
                            QualifyingDetailScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                        composable<Route.LapTimes> {
                            LapTimesScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                        composable<Route.PitStops> {
                            PitStopsScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                    }

                    // Search tab graph and its sub-screens
                    navigation<Route.SearchGraph>(startDestination = Route.Search) {
                        composable<Route.Search> {
                            SearchScreenRoot(
                                onDriverClick = { driver ->
                                    navController.navigate(
                                        Route.DriverDetail(
                                            driverId = driver.id
                                        )
                                    )
                                },
                                onConstructorClick = { constructor ->
                                    navController.navigate(
                                        Route.ConstructorDetail(
                                            constructorId = constructor.id
                                        )
                                    )
                                },
                                onCircuitClick = { circuit ->
                                    navController.navigate(
                                        Route.CircuitDetail(
                                            circuitId = circuit.id
                                        )
                                    )
                                }
                            )
                        }
                        composable<Route.DriverDetail> {
                            DriverDetailScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                        composable<Route.ConstructorDetail> {
                            ConstructorDetailScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                        composable<Route.CircuitDetail> {
                            CircuitDetailScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}
