package com.example.purpleapex.app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.purpleapex.circuit.presentation.circuit_detail.CircuitDetailScreenRoot
import com.example.purpleapex.constructor.presentation.constructor_detail.ConstructorDetailScreenRoot
import com.example.purpleapex.driver.presentation.driver_detail.DriverDetailScreenRoot
import com.example.purpleapex.grandprix.presentation.GrandPrixDetailScreenRoot
import com.example.purpleapex.grandprix.presentation.grand_prix_list.GrandPrixListScreenRoot
import com.example.purpleapex.home.presentation.HomeScreenRoot
import com.example.purpleapex.lap.presentation.LapTimesScreenRoot
import com.example.purpleapex.standings.presentation.standings_list.StandingsListScreenRoot
import com.example.purpleapex.ui.theme.AppTheme

val LocalScaffoldPadding = staticCompositionLocalOf { PaddingValues(0.dp) }
val LocalTopSafePadding = staticCompositionLocalOf { PaddingValues(0.dp) }

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = { graph, root, reselected ->
                        // Delegate to a small testable helper via a NavController-backed adapter.
                        val navigator = object : TabNavigator {
                            override fun popBackStack(route: Route, inclusive: Boolean): Boolean =
                                navController.popBackStack(route, inclusive)

                            override fun navigateToGraph(graph: Route) {
                                navController.navigate(graph) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo<Route.Graph> { saveState = true }
                                }
                            }
                        }
                        handleBottomBarNavigation(
                            navigator = navigator,
                            graph = graph,
                            root = root,
                            reselected = reselected,
                        )
                    },
                )
            },
        ) { innerPadding ->
            CompositionLocalProvider(
                LocalScaffoldPadding provides innerPadding,
                LocalTopSafePadding provides WindowInsets.statusBars.asPaddingValues(),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Route.Graph,
                    modifier = Modifier
                ) {
                    navigation<Route.Graph>(
                        startDestination = Route.HomeGraph
                    ) {
                        // Home tab graph
                        navigation<Route.HomeGraph>(startDestination = Route.Home) {
                            composable<Route.Home> {
                                HomeScreenRoot(
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

                        // Standings tab graph
                        navigation<Route.StandingsGraph>(startDestination = Route.Standings) {
                            composable<Route.Standings> {
                                StandingsListScreenRoot()
                            }
                        }

                        // Racing tab graph and its sub-screens
                        navigation<Route.RacingGraph>(startDestination = Route.Racing) {
                            composable<Route.Racing> {
                                GrandPrixListScreenRoot(
                                    onGrandPrixClick = { season, round ->
                                        navController.navigate(
                                            Route.GrandPrixDetail(
                                                season = season,
                                                round = round,
                                            )
                                        )
                                    }
                                )
                            }
                            composable<Route.GrandPrixDetail> {
                                GrandPrixDetailScreenRoot(
                                    onBackClick = { navController.navigateUp() },
                                    onLapTimesClick = { season, round ->
                                        navController.navigate(Route.LapTimes(season, round))
                                    },
                                )
                            }
                            composable<Route.LapTimes> {
                                LapTimesScreenRoot(
                                    onBackClick = { navController.navigateUp() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
