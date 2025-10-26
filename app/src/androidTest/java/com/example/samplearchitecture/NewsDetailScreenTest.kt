package com.example.samplearchitecture

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.samplearchitecture.ui.detail.DetailScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsScreen_displaysArticleDetails() {
        val article = com.example.samplearchitecture.data.Article(
            title = "Sample Article",
            description = "This is a sample article for testing.",
            url = "https://example.com/sample-article",
            imageUrl = null,
            publishedAt = "2024-01-01T00:00:00Z",
            content = "Sample content of the article."
        )

        composeTestRule.setContent {
            DetailScreen(
                onBackClick = {},
                article = article
            )
        }

        composeTestRule.onNodeWithText(article.title).assertExists()
        composeTestRule.onNodeWithText(article.description!!).assertExists()
    }
}