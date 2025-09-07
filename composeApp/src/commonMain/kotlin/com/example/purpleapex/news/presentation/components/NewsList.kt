package com.example.purpleapex.news.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.purpleapex.news.domain.NewsArticle
import com.example.purpleapex.news.presentation.NewsListState

@Composable
fun NewsList(
    state: NewsListState,
    onArticleClick: (NewsArticle) -> Unit,
    onUrlClick: (String) -> Unit,
    onArticleClose: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
) {
    when {
        state.isLoading -> Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        state.errorMessage != null -> Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)

        else -> LazyRow(
            modifier = modifier.fillMaxWidth(),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.articles, key = { it.id }) { article ->
                NewsCarouselItem(
                    article = article,
                    onClick = { onArticleClick(article) }
                )
            }
        }
    }

    if (state.selectedArticle != null) {
        AlertDialog(
            onDismissRequest = onArticleClose,
            confirmButton = {
                TextButton(onClick = onArticleClose) {
                    Text("Close")
                }
            },
            dismissButton = {
                if (!state.selectedArticle.linkUrl.isNullOrBlank()) {
                    TextButton(onClick = { onUrlClick(state.selectedArticle.linkUrl) }) {
                        Text("Read more")
                    }
                }
            },
            title = {
                Text(
                    text = state.selectedArticle.headline,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            },
            text = {
                Column {
                    if (!state.selectedArticle.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = state.selectedArticle.imageUrl,
                            contentDescription = state.selectedArticle.headline,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    if (!state.selectedArticle.description.isNullOrBlank()) {
                        Text(text = state.selectedArticle.description, style = MaterialTheme.typography.bodyMedium)
                    } else {
                        Text(
                            text = "No description available",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            textContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}
