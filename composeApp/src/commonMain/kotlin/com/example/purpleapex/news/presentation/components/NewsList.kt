package com.example.purpleapex.news.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.purpleapex.news.domain.NewsArticle
import com.example.purpleapex.news.presentation.NewsState

@Composable
fun NewsList(
    state: NewsState,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (selectedArticle, setSelectedArticle) = remember { mutableStateOf<NewsArticle?>(null) }

    when {
        state.isLoading -> {
            Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Text(text = state.error ?: "Error", color = MaterialTheme.colorScheme.error)
        }

        else -> {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.articles, key = { it.id }) { article ->
                    NewsCarouselItem(article = article, onClick = { setSelectedArticle(article) })
                }
            }
        }
    }

    val article = selectedArticle
    if (article != null) {
        AlertDialog(
            onDismissRequest = { setSelectedArticle(null) },
            confirmButton = {
                TextButton(onClick = { setSelectedArticle(null) }) {
                    Text("Close")
                }
            },
            dismissButton = {
                if (!article.linkUrl.isNullOrBlank()) {
                    TextButton(onClick = { onArticleClick(article.linkUrl!!) }) {
                        Text("Open Link")
                    }
                }
            },
            title = {
                Text(
                    text = article.headline,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            },
            text = {
                Column {
                    if (!article.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = article.imageUrl,
                            contentDescription = article.headline,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    if (!article.description.isNullOrBlank()) {
                        Text(text = article.description!!, style = MaterialTheme.typography.bodyMedium)
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
