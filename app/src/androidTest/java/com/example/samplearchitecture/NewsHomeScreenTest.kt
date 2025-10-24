package com.example.samplearchitecture

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.samplearchitecture.data.Article
import com.example.samplearchitecture.ui.home.NewsHomeScreen
import com.example.samplearchitecture.ui.home.NewsHomeUiState
import com.example.samplearchitecture.ui.theme.SampleArchitectureTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsHomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun circularProgressIndicator_whenScreenIsLoading_exists() {
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = true, articles = emptyList()),
                        onItemClick = {}
                    )
                }
            }
        }

        // Add assertions to verify the presence of CircularProgressIndicator when loading
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
    }

    @Test
    fun circularProgressIndicator_whenScreenIsNotLoading_doesNotExist() {
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = emptyList()),
                        onItemClick = {}
                    )
                }
            }
        }

        // Add assertions to verify the absence of CircularProgressIndicator when not loading
        composeTestRule.onNodeWithTag("loading_indicator").assertDoesNotExist()
    }

    @Test
    fun errorMessage_whenScreenHasError_displaysErrorMessage() {
        val errorMessage = "Failed to load news"
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = emptyList(), errorMessage = errorMessage),
                        onItemClick = {}
                    )
                }
            }
        }

        // Add assertions to verify the presence of error message when there is an error
        composeTestRule.onNodeWithTag("error_message").assertExists()
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun showsListOfArticles_whenScreenHasArticles_displaysArticles() {
        val articles = listOf(
            Article(
                title = "Article 1",
                description = "Description 1",
                url = "https://example.com/article1",
                imageUrl = null,
                publishedAt = "2024-01-01T00:00:00Z",
                content = "Content 1",
            ),
            Article(
                title = "Article 2",
                description = "Description 2",
                url = "https://example.com/article2",
                imageUrl = null,
                publishedAt = "2024-01-02T00:00:00Z",
                content = "Content 2",
            )
        )
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = articles),
                        onItemClick = {}
                    )
                }
            }
        }

        // Add assertions to verify the presence of articles in the list
        articles.forEach { article ->
            composeTestRule.onNodeWithText(article.title).assertExists()
        }
    }
}