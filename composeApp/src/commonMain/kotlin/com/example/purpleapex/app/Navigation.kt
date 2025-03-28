package com.example.purpleapex.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy

enum class TopLevelRoutes(val title: String, val route: Route, val icon: ImageVector) {
    STANDINGS("Standings", Route.Standings, Icons.Rounded.Leaderboard),
    RESULTS("Results", Route.Results, Icons.Rounded.EmojiEvents),
    HOME("Home", Route.Home, Icons.Rounded.Home),
    DRIVERS("Drivers", Route.DriverList, Icons.Rounded.Person)
}

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (Route) -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        TopLevelRoutes.entries.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.title) },
                label = {
                    Text(
                        topLevelRoute.title,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route.toString() } == true,
                onClick = { onNavigate(topLevelRoute.route) },
            )
        }
    }
}
