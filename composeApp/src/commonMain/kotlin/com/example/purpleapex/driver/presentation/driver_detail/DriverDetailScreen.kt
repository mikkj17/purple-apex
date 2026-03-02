package com.example.purpleapex.driver.presentation.driver_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.driver.domain.DriverDetail
import com.example.purpleapex.driver.presentation.driver_detail.components.DriverDetailMenu
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import purpleapex.composeapp.generated.resources.*

private fun getDriverImage(driver: DriverDetail): DrawableResource? {
    return when (driver.id) {
        "albon" -> Res.drawable.driver_albon
        "alonso" -> Res.drawable.driver_alonso
        "antonelli" -> Res.drawable.driver_antonelli
        "bearman" -> Res.drawable.driver_bearman
        "bortoleto" -> Res.drawable.driver_bortoleto
        "bottas" -> Res.drawable.driver_bottas
        "colapinto" -> Res.drawable.driver_colapinto
        "gasly" -> Res.drawable.driver_gasly
        "hadjar" -> Res.drawable.driver_hadjar
        "hamilton" -> Res.drawable.driver_hamilton
        "hulkenberg" -> Res.drawable.driver_hulkenberg
        "lawson" -> Res.drawable.driver_lawson
        "leclerc" -> Res.drawable.driver_leclerc
        "lindblad" -> Res.drawable.driver_lindblad
        "max_verstappen" -> Res.drawable.driver_max_verstappen
        "norris" -> Res.drawable.driver_norris
        "ocon" -> Res.drawable.driver_ocon
        "perez" -> Res.drawable.driver_perez
        "piastri" -> Res.drawable.driver_piastri
        "russell" -> Res.drawable.driver_russell
        "sainz" -> Res.drawable.driver_sainz
        "stroll" -> Res.drawable.driver_stroll
        else -> null
    }
}

@Composable
fun DriverDetailScreenRoot(
    viewModel: DriverDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onGrandPrixClick: (Int, Int) -> Unit,
    onHistoryClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DriverDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DriverDetailAction.OnBackClick -> onBackClick()
                is DriverDetailAction.OnGrandPrixClick -> onGrandPrixClick(action.season, action.round)
                is DriverDetailAction.OnHistoryClick -> onHistoryClick(state.driver!!.id)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun DriverDetailScreen(
    state: DriverDetailState,
    onAction: (DriverDetailAction) -> Unit,
) {
    val teamColor = state.driver?.color?.let { Color(it.drop(1).toInt(16)) } ?: MaterialTheme.colorScheme.background
    val driverImageResource = state.driver?.let { getDriverImage(it) }
    val isDark = isSystemInDarkTheme()

    val backgroundColor = Brush.verticalGradient(
        colors = listOf(
            teamColor.copy(alpha = 0.8f),
            MaterialTheme.colorScheme.background,
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Large background number
        state.driver?.number?.let { number ->
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 240.sp,
                    fontWeight = FontWeight.Black
                ),
                color = Color.White.copy(alpha = 0.05f),
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        // Driver Image (Full image on the right, spanning full height)
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.6f)
                .align(Alignment.BottomEnd),
        ) {
            if (state.driver != null && driverImageResource != null) {
                Image(
                    painter = painterResource(driverImageResource),
                    contentDescription = state.driver.fullName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.BottomCenter
                )
            } else if (state.driver != null) {
                Text(
                    text = "${state.driver.givenName.firstOrNull() ?: ""}${state.driver.familyName.firstOrNull() ?: ""}",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White.copy(alpha = 0.1f),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        // Scrollable content on the left
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Padding for header actions

            state.driver?.let { driver ->
                Column(
                    modifier = Modifier.widthIn(max = 400.dp)
                ) {
                    Text(
                        text = driver.givenName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (isDark) Color.White else Color.Black,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = driver.familyName.uppercase(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (isDark) Color.White else Color.Black,
                        fontWeight = FontWeight.Black,
                        softWrap = true,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    driver.nationality?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isDark) Color.White else Color.Black
                        )
                    }
                    driver.dateOfBirth?.let {
                        Text(
                            text = it.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isDark) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = state.constructors.joinToString("\n") { it.name },
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isDark) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    state.driverStats?.let { stats ->
                        Text(
                            text = "CAREER STATS",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isDark) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StatRow(
                            label = "Grand Prix entered",
                            value = stats.grandsPrixEntered.toString(),
                            isDark = isDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val highestFinish = stats.highestRaceFinish
                        val hasWon = highestFinish == 1
                        val highestFinishSuffix = if (stats.highestRaceFinishCount > 1)
                            " (${stats.highestRaceFinishCount}x)"
                        else ""
                        StatRow(
                            label = if (hasWon) "Wins" else "Highest race finish",
                            value = if (hasWon) stats.highestRaceFinishCount.toString()
                            else highestFinish?.let { "P$it$highestFinishSuffix" } ?: "—",
                            isDark = isDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        StatRow(label = "Podiums", value = stats.podiums.toString(), isDark = isDark)
                        Spacer(modifier = Modifier.height(12.dp))

                        val highestGrid = stats.highestGrid
                        val hasPole = highestGrid == 1
                        val highestGridSuffix =
                            if (stats.highestGridCount > 1) " (${stats.highestGridCount}x)" else ""
                        StatRow(
                            label = if (hasPole) "Pole positions" else "Highest grid position",
                            value = if (hasPole) stats.highestGridCount.toString()
                            else highestGrid?.let { "P$it$highestGridSuffix" } ?: "—",
                            isDark = isDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        StatRow(label = "DNFs", value = stats.dnfs.toString(), isDark = isDark)
                    }
                }
            }
        }

        // Header Actions - overlay on top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalTopSafePadding.current)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onAction(DriverDetailAction.OnBackClick) },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Go back",
                    tint = if (isDark) Color.White else Color.Black
                )
            }

            DriverDetailMenu(
                onAction = onAction,
                tint = if (isDark) Color.White else Color.Black
            )
        }

        // Loading and Error states
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = if (isDark) Color.White else teamColor
            )
        }

        state.errorMessage?.let { error ->
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (isDark) Color.White else Color.Black,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onAction(DriverDetailAction.OnRetryClick) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDark) Color.White else teamColor,
                        contentColor = if (isDark) teamColor else Color.White
                    )
                ) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String, isDark: Boolean) {
    Column {
        val textColor = if (isDark) Color.White else Color.Black
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = textColor.copy(alpha = 0.7f),
        )
    }
}
