package com.example.purpleapex.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer

private object GraphRoutes {
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T : Route> serialName(): String =
        serializer<T>().descriptor.serialName

    val home by lazy { serialName<Route.HomeGraph>() }
    val standings by lazy { serialName<Route.StandingsGraph>() }
    val racing by lazy { serialName<Route.RacingGraph>() }
}

enum class TopLevelRoutes(
    val title: String,
    val graph: Route,
    val root: Route,
    val icon: ImageVector
) {
    HOME("Home", Route.HomeGraph, Route.Home, Icons.Rounded.Home),
    STANDINGS("Standings", Route.StandingsGraph, Route.Standings, Icons.Rounded.Leaderboard),
    RACING("Racing", Route.RacingGraph, Route.Racing, Icons.Rounded.RocketLaunch),
}

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (graph: Route, root: Route, reselected: Boolean) -> Unit,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        TopLevelRoutes.entries.forEach { topLevelRoute ->
            val isSelected = currentDestination
                ?.hierarchy
                ?.any { dest ->
                    when (topLevelRoute) {
                        TopLevelRoutes.HOME -> dest.hasRoute(GraphRoutes.home, null)
                        TopLevelRoutes.STANDINGS -> dest.hasRoute(GraphRoutes.standings, null)
                        TopLevelRoutes.RACING -> dest.hasRoute(GraphRoutes.racing, null)
                    }
                } ?: false

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
                onClick = { onNavigate(topLevelRoute.graph, topLevelRoute.root, isSelected) },
                selected = isSelected,
            )
        }
    }
}
