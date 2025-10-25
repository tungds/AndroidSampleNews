package com.example.samplearchitecture.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.samplearchitecture.data.Article


@Composable
internal fun NewsHomeScreen(
    newsHomeViewModel: NewsHomeViewModel = hiltViewModel(),
    onItemClick: (Article) -> Unit
) {
    val uiState by newsHomeViewModel.uiState.collectAsStateWithLifecycle()
    NewsHomeScreen(
        uiState,
        onItemClick,
        onSearchChange = { newsHomeViewModel.searchNews(it) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHomeScreen(
    uiState: NewsHomeUiState,
    onItemClick: (Article) -> Unit,
    onSearchChange: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    Scaffold(topBar = {
        Column(Modifier.statusBarsPadding()) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    onSearchChange(it)
                },
                placeholder = { Text("Search news...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .testTag("search_field"),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        Modifier
                            .align(Alignment.Center)
                            .testTag("loading_indicator")
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("error_message")
                    )
                }

                else -> {
                    LazyColumn(Modifier.testTag("articles_list")) {
                        items(uiState.articles) {
                            ArticleItem(article = it, onItemClick = onItemClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onItemClick: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(article) }),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            article.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(8.dp))
            }
            Text(article.title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(article.content ?: "", maxLines = 3, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(4.dp))
            Text(
                text = article.publishedAt,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    NewsHomeScreen(onItemClick = {})
}