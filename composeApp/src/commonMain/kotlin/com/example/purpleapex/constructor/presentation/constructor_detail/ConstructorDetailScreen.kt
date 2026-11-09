package com.example.purpleapex.constructor.presentation.constructor_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.app.LocalTopSafePadding
import com.example.purpleapex.constructor.presentation.constructor_detail.components.ConstructorDetailMenu
import com.example.purpleapex.core.presentation.components.Header
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import purpleapex.composeapp.generated.resources.*

fun getConstructorLogo(constructorId: String): DrawableResource? {
    return when (constructorId) {
        "alpine" -> Res.drawable.constructor_alpine
        "aston_martin" -> Res.drawable.constructor_aston_martin
        "audi" -> Res.drawable.constructor_audi
        "cadillac" -> Res.drawable.constructor_cadillac
        "ferrari" -> Res.drawable.constructor_ferrari
        "haas" -> Res.drawable.constructor_haas
        "mclaren" -> Res.drawable.constructor_mclaren
        "mercedes" -> Res.drawable.constructor_mercedes
        "rb" -> Res.drawable.constructor_rb
        "red_bull" -> Res.drawable.constructor_red_bull
        "williams" -> Res.drawable.constructor_williams
        else -> null
    }
}

fun getConstructorCar(constructorId: String): DrawableResource? {
    return when (constructorId) {
        "alpine" -> Res.drawable.car_alpine
        "aston_martin" -> Res.drawable.car_aston_martin
        "audi" -> Res.drawable.car_audi
        "cadillac" -> Res.drawable.car_cadillac
        "ferrari" -> Res.drawable.car_ferrari
        "haas" -> Res.drawable.car_haas
        "mclaren" -> Res.drawable.car_mclaren
        "mercedes" -> Res.drawable.car_mercedes
        "rb" -> Res.drawable.car_rb
        "red_bull" -> Res.drawable.car_red_bull
        "williams" -> Res.drawable.car_williams
        else -> null
    }
}


@Composable
fun ConstructorDetailScreenRoot(
    viewModel: ConstructorDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onGrandPrixClick: (Int, Int) -> Unit,
    onHistoryClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ConstructorDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ConstructorDetailAction.OnBackClick -> onBackClick()
                is ConstructorDetailAction.OnGrandPrixClick -> onGrandPrixClick(action.season, action.round)
                is ConstructorDetailAction.OnHistoryClick -> onHistoryClick(state.constructor!!.id)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun ConstructorDetailScreen(
    state: ConstructorDetailState,
    onAction: (ConstructorDetailAction) -> Unit,
) {
    val teamColor = state.constructor?.color
        ?.let { Color(it.drop(1).toInt(16)).copy(alpha = 1f) }
        ?: MaterialTheme.colorScheme.background
    val constructorLogoResource = state.constructor?.let { getConstructorLogo(it.id) }
    val constructorCarResource = state.constructor?.let { getConstructorCar(it.id) }
    val isDark = isSystemInDarkTheme()

    val backgroundColor = Brush.verticalGradient(
        colors = listOf(
            teamColor,
            MaterialTheme.colorScheme.background,
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.errorMessage != null -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = state.errorMessage,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onAction(ConstructorDetailAction.OnRetryClick) }) { Text("Retry") }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Header(
                        onBackClick = {
                            onAction(ConstructorDetailAction.OnBackClick)
                        },
                        modifier = Modifier.padding(LocalTopSafePadding.current),
                        backgroundColor = Color.Transparent,
                        trailingContent = {
                            ConstructorDetailMenu(
                                onAction = onAction,
                                tint = if (isDark) Color.White else Color.Black
                            )
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = LocalScaffoldPadding.current.calculateBottomPadding())
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (state.constructor != null && constructorLogoResource != null) {
                                Image(
                                    painter = painterResource(constructorLogoResource),
                                    contentDescription = state.constructor.name,
                                    modifier = Modifier.fillMaxWidth().size(128.dp),
                                    contentScale = ContentScale.Fit,
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            state.constructor?.let { constructor ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = constructor.name.uppercase(),
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = if (isDark) Color.White else Color.Black,
                                        fontWeight = FontWeight.Black,
                                        softWrap = true,
                                        maxLines = 3,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = constructor.nationality,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = if (isDark) Color.White else Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(32.dp))
                                }
                            }

                            state.constructorStats?.let { stats ->
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "TEAM STATS",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (isDark) Color.White else Color.Black,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    StatRow(
                                        label = "Grands Prix entered",
                                        value = stats.grandsPrixEntered.toString(),
                                        isDark = isDark
                                    )

                                    val highestFinish = stats.highestRaceFinish
                                    val hasWon = highestFinish == 1
                                    val highestFinishSuffix =
                                        if (stats.highestRaceFinishCount > 1) " (${stats.highestRaceFinishCount}x)" else ""
                                    StatRow(
                                        label = if (hasWon) "Wins" else "Highest race finish",
                                        value = if (hasWon) stats.highestRaceFinishCount.toString() else highestFinish?.let { "P$it$highestFinishSuffix" }
                                            ?: "—",
                                        isDark = isDark
                                    )

                                    StatRow(label = "Podiums", value = stats.podiums.toString(), isDark = isDark)

                                    val highestGrid = stats.highestGrid
                                    val hasPole = highestGrid == 1
                                    val highestGridSuffix =
                                        if (stats.highestGridCount > 1) " (${stats.highestGridCount}x)" else ""
                                    StatRow(
                                        label = if (hasPole) "Pole positions" else "Highest grid position",
                                        value = if (hasPole) stats.highestGridCount.toString() else highestGrid?.let { "P$it$highestGridSuffix" }
                                            ?: "—",
                                        isDark = isDark
                                    )
                                }
                            }
                        }

                        if (state.constructor != null && constructorCarResource != null) {
                            Image(
                                painter = painterResource(constructorCarResource),
                                contentDescription = "${state.constructor.name} car",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp)
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 16.dp),
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String, isDark: Boolean) {
    Column(
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        val textColor = if (isDark) Color.White else Color.Black
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = textColor.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}
