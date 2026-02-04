package com.example.purpleapex.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.liquid
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
    STANDINGS("Standings", Route.StandingsGraph, Route.Standings, Icons.Rounded.Leaderboard),
    HOME("Home", Route.HomeGraph, Route.Home, Icons.Rounded.Home),
    RACING("Racing", Route.RacingGraph, Route.Racing, Icons.Rounded.RocketLaunch),
}

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (graph: Route, root: Route, reselected: Boolean) -> Unit,
) {
    val liquidState = LocalLiquidState.current
    val shape = RoundedCornerShape(size = 64.dp)
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .padding(bottom = 16.dp)
            .liquid(liquidState) {
                frost = 48.dp
                this.shape = shape
            }
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = shape
            )
            .border(
                border = BorderStroke(width = 1.dp, color = Color.White.copy(alpha = 0.2f)),
                shape = shape
            ),
    ) {
        Row(modifier = Modifier.liquefiable(liquidState)) {
            TopLevelRoutes.entries.forEach { topLevelRoute ->
                val isSelected = currentDestination
                    ?.hierarchy
                    ?.any { dest ->
                        when (topLevelRoute) {
                            TopLevelRoutes.STANDINGS -> dest.hasRoute(GraphRoutes.standings, null)
                            TopLevelRoutes.HOME -> dest.hasRoute(GraphRoutes.home, null)
                            TopLevelRoutes.RACING -> dest.hasRoute(GraphRoutes.racing, null)
                        }
                    } ?: false

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = topLevelRoute.icon,
                            contentDescription = topLevelRoute.title,
                            modifier = Modifier.size(if (isSelected) 24.dp else 32.dp),
                        )
                    },
                    label = {
                        Text(
                            topLevelRoute.title,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    onClick = { onNavigate(topLevelRoute.graph, topLevelRoute.root, isSelected) },
                    selected = isSelected,
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                    )
                )
            }
        }
    }
}
