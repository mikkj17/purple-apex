package com.example.purpleapex.app

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination

enum class TopLevelRoutes(val title: String, val route: Route, val icon: ImageVector) {
    HOME("Home", Route.Home, Icons.Rounded.Home),
    STANDINGS("Standings", Route.Standings, Icons.Rounded.Leaderboard),
    RACING("Racing", Route.Racing, Icons.Rounded.RocketLaunch),
    DRIVERS("Search", Route.Search, Icons.Rounded.Search),
}

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (Route, Boolean) -> Unit,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        TopLevelRoutes.entries.forEach { topLevelRoute ->
            val isSelected = run {
                val routeString = currentDestination?.route.orEmpty()
                when (topLevelRoute) {
                    TopLevelRoutes.HOME -> routeString.contains("Route.Home") || routeString.endsWith(".Home")
                    TopLevelRoutes.STANDINGS -> routeString.contains("Route.Standings") || routeString.endsWith(".Standings")
                    TopLevelRoutes.RACING -> routeString.contains("Route.Racing") ||
                            routeString.contains("Route.RaceDetail") ||
                            routeString.contains("Route.QualifyingDetail") ||
                            routeString.contains("Route.LapTimes") ||
                            routeString.contains("Route.PitStops") ||
                            routeString.endsWith(".Racing") ||
                            routeString.endsWith(".RaceDetail") ||
                            routeString.endsWith(".QualifyingDetail") ||
                            routeString.endsWith(".LapTimes") ||
                            routeString.endsWith(".PitStops")
                    TopLevelRoutes.DRIVERS -> routeString.contains("Route.Search") ||
                            routeString.contains("Route.DriverDetail") ||
                            routeString.contains("Route.ConstructorDetail") ||
                            routeString.contains("Route.CircuitDetail") ||
                            routeString.endsWith(".Search") ||
                            routeString.endsWith(".DriverDetail") ||
                            routeString.endsWith(".ConstructorDetail") ||
                            routeString.endsWith(".CircuitDetail")
                }
            }
            NavigationBarItem(
                icon = {
                    Icon(
                        topLevelRoute.icon,
                        contentDescription = topLevelRoute.title,
                        modifier = Modifier.size(32.dp)
                    )
                },
                label = {
                    Text(
                        topLevelRoute.title,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                onClick = { onNavigate(topLevelRoute.route, isSelected) },
                selected = isSelected,
            )
        }
    }
}
