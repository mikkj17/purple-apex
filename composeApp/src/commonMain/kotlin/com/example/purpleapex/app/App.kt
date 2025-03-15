package com.example.purpleapex.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.purpleapex.driver.presentation.driver_detail.DriverDetailScreenRoot
import com.example.purpleapex.driver.presentation.driver_list.DriverListScreenRoot
import com.example.purpleapex.driver.presentation.driver_list.DriverListViewModel
import com.example.purpleapex.home.presentation.HomeScreenRoot
import com.example.purpleapex.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

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
                    onNavigate = { navController.navigate(it) }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.Graph,
                modifier = Modifier.padding(innerPadding)
            ) {
                navigation<Route.Graph>(
                    startDestination = Route.Home
                ) {
                    composable<Route.Home> {
                        HomeScreenRoot()
                    }
                    composable<Route.DriverList> {
                        val viewModel = koinViewModel<DriverListViewModel>()
                        DriverListScreenRoot(
                            viewModel = viewModel,
                            onDriverClick = { driver ->
                                navController.navigate(
                                    Route.DriverDetail(
                                        driverId = driver.id
                                    )
                                )
                            }
                        )
                    }
                    composable<Route.DriverDetail> {
                        DriverDetailScreenRoot()
                    }
                }
            }
        }
    }
}
