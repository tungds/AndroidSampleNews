package com.example.samplearchitecture

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.samplearchitecture.data.Article
import com.example.samplearchitecture.ui.home.NewsHomeScreen
import com.example.samplearchitecture.ui.home.NewsHomeUiState
import com.example.samplearchitecture.ui.theme.SampleArchitectureTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsHomeScreenTest {
    
    companion object{
        private const val TAG_SEARCH_FIELD = "search_field"
        private const val TAG_ARTICLES_LIST = "articles_list"
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    private val listArticle = listOf(
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

    @Test
    fun circularProgressIndicator_whenScreenIsLoading_exists() {
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = true, articles = emptyList()),
                        onItemClick = {},
                        onSearchChange = {}
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
                        onItemClick = {},
                        onSearchChange = {}
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
                        uiState = NewsHomeUiState(
                            isLoading = false,
                            articles = emptyList(),
                            errorMessage = errorMessage
                        ),
                        onItemClick = {},
                        onSearchChange = {}
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
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = listArticle),
                        onItemClick = {},
                        onSearchChange = {}
                    )
                }
            }
        }

        // Add assertions to verify the presence of articles in the list
        listArticle.forEach { article ->
            composeTestRule.onNodeWithText(article.title).assertExists()
        }
    }

    @Test
    fun searchField_whenTextEntered_returnOneResult() {
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = listArticle),
                        onItemClick = {},
                        onSearchChange = {}
                    )
                }
            }
        }

        val searchQuery = "Article 1"
        val searchField = composeTestRule.onNodeWithTag(TAG_SEARCH_FIELD)
        searchField.assertExists()
        searchField.performTextInput(searchQuery)
        // Verify the search result includes the expected article
        composeTestRule.onNodeWithTag(TAG_ARTICLES_LIST).assertIsDisplayed()
        val result = composeTestRule.onNode(
            hasText(searchQuery) and hasParent(hasTestTag(TAG_ARTICLES_LIST))
        )
        result.assertIsDisplayed()

        composeTestRule.onAllNodes(
            hasText(searchQuery) and hasParent(hasTestTag(TAG_ARTICLES_LIST))
        ).assertCountEquals(1)
    }

    @Test
    fun searchField_whenNoMatchingText_returnNoResults() {
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = listArticle),
                        onItemClick = {},
                        onSearchChange = {}
                    )
                }
            }
        }

        val searchQuery = "Nonexistent Article"
        val searchField = composeTestRule.onNodeWithTag(TAG_SEARCH_FIELD)
        searchField.assertExists()
        searchField.performTextInput(searchQuery)
        // Verify that no articles are displayed in the list
        composeTestRule.onNodeWithTag(TAG_ARTICLES_LIST).assertIsDisplayed()
        val result = composeTestRule.onNode(
            hasText(searchQuery) and hasParent(hasTestTag(TAG_ARTICLES_LIST))
        )
        result.assertDoesNotExist()
    }

    @Test
    fun searchField_whenEmptyText_returnAllResults() {
        composeTestRule.setContent {
            SampleArchitectureTheme {
                Surface {
                    NewsHomeScreen(
                        uiState = NewsHomeUiState(isLoading = false, articles = listArticle),
                        onItemClick = {},
                        onSearchChange = {}
                    )
                }
            }
        }

        val searchQuery = ""
        val searchField = composeTestRule.onNodeWithTag(TAG_SEARCH_FIELD)
        searchField.assertExists()
        searchField.performTextInput(searchQuery)
        // Verify that all articles are displayed in the list
        composeTestRule.onNodeWithTag(TAG_ARTICLES_LIST).assertIsDisplayed()
        listArticle.forEach { article ->
            composeTestRule.onNodeWithText(article.title).assertExists()
        }
    }
}