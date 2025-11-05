package com.example.purpleapex.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.purpleapex.app.LocalScaffoldPadding
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.news.presentation.NewsListAction
import com.example.purpleapex.news.presentation.NewsListState
import com.example.purpleapex.news.presentation.NewsListViewModel
import com.example.purpleapex.news.presentation.components.NewsList
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import purpleapex.composeapp.generated.resources.Res
import purpleapex.composeapp.generated.resources.purple_apex_logo

@Composable
fun HomeScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { viewModel.onAction(it) },
    )
}

@Composable
fun HomeScreen(
    state: NewsListState,
    onAction: (NewsListAction) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
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
                        state = state,
                        onArticleClick = {
                            onAction(NewsListAction.OnArticleClick(it))
                        },
                        onUrlClick = { url ->
                            if (url.isNotBlank()) uriHandler.openUri(url)
                        },
                        onArticleClose = {
                            onAction(NewsListAction.OnArticleClose)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
