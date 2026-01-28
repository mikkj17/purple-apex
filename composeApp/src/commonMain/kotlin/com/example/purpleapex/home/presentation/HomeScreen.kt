package com.example.purpleapex.home.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.news.presentation.NewsListAction
import com.example.purpleapex.news.presentation.NewsListState
import com.example.purpleapex.news.presentation.NewsListViewModel
import com.example.purpleapex.news.presentation.components.NewsList
import com.example.purpleapex.search.presentation.SearchAction
import com.example.purpleapex.search.presentation.SearchOverlay
import com.example.purpleapex.search.presentation.SearchState
import com.example.purpleapex.search.presentation.SearchViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import purpleapex.composeapp.generated.resources.Res
import purpleapex.composeapp.generated.resources.purple_apex_logo

@Composable
fun HomeScreenRoot(
    newsViewModel: NewsListViewModel = koinViewModel(),
    searchViewModel: SearchViewModel = koinViewModel(),
    onDriverClick: (Driver) -> Unit,
    onConstructorClick: (Constructor) -> Unit,
    onCircuitClick: (Circuit) -> Unit,
) {
    val newsState by newsViewModel.state.collectAsStateWithLifecycle()
    val searchState by searchViewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        newsState = newsState,
        searchState = searchState,
        onNewsAction = { newsViewModel.onAction(it) },
        onSearchAction = { action ->
            when (action) {
                is SearchAction.OnDriverClick -> onDriverClick(action.driver)
                is SearchAction.OnConstructorClick -> onConstructorClick(action.constructor)
                is SearchAction.OnCircuitClick -> onCircuitClick(action.circuit)
                else -> Unit
            }
            searchViewModel.onAction(action)
        },
    )
}

@Composable
private fun HomeScreen(
    newsState: NewsListState,
    searchState: SearchState,
    onNewsAction: (NewsListAction) -> Unit,
    onSearchAction: (SearchAction) -> Unit,
) {
    val blurAlpha by animateFloatAsState(
        targetValue = if (searchState.showSearchOverlay) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "blurAlpha"
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(searchState.showSearchOverlay) {
        if (!searchState.showSearchOverlay) {
            keyboardController?.hide()
        }
    }

    val showOverlay = searchState.showSearchOverlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(showOverlay) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (dragAmount > 20 && !showOverlay) {
                        onSearchAction(SearchAction.OnToggleSearchOverlay(true))
                    } else if (dragAmount < -20 && showOverlay) {
                        onSearchAction(SearchAction.OnToggleSearchOverlay(false))
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = (blurAlpha * 15).dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.purple_apex_logo),
                contentDescription = "Purple Apex Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            AppCard(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(LocalScaffoldPadding.current)
                    .padding(horizontal = 8.dp, vertical = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Latest news",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    run {
                        val uriHandler = LocalUriHandler.current
                        NewsList(
                            state = newsState,
                            onArticleClick = {
                                onNewsAction(NewsListAction.OnArticleClick(it))
                            },
                            onUrlClick = { url ->
                                if (url.isNotBlank()) uriHandler.openUri(url)
                            },
                            onArticleClose = {
                                onNewsAction(NewsListAction.OnArticleClose)
                            },
                            onRetryClick = {
                                onNewsAction(NewsListAction.OnRetryClick)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        if (searchState.showSearchOverlay || blurAlpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = blurAlpha
                    }
                    .background(MaterialTheme.colorScheme.background.copy(alpha = blurAlpha * 0.4f))
                    .zIndex(1f)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onSearchAction(SearchAction.OnToggleSearchOverlay(false))
                        }
                    }
            ) {
                SearchOverlay(
                    state = searchState,
                    onAction = { action ->
                        onSearchAction(action)
                    }
                )
            }
        }
    }
}
