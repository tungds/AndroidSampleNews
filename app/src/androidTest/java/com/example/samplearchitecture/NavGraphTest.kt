package com.example.samplearchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
//@HiltAndroidTest
@LargeTest
class NavGraphTest {

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule(order = 0)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        // hiltRule.inject()
    }

    @Test
    fun homescreen_clickNewsItem_navigateToDetailScreen() = runTest {
        //setContent()

        // Verify that the home screen is displayed
        composeTestRule.onNodeWithTag("articles_list").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("articles_list").fetchSemanticsNodes().isNotEmpty()
        // Click on the first news item
        composeTestRule.onNodeWithTag("articles_list")
            .onChildAt(0)
            .performClick()

        // Verify that the detail screen is displayed
        composeTestRule.onNodeWithTag("detail_screen").assertExists()
    }

    @Test
    fun homescreen_searchNews_clickNewsItem_navigateToDetailScreen() = runTest {
        //setContent()

        // Verify that the home screen is displayed
        composeTestRule.onNodeWithTag("articles_list").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("articles_list").fetchSemanticsNodes().isNotEmpty()

        // Enter search query
        val searchQuery = "A"
        composeTestRule.onNodeWithTag("search_field")
            .performClick()
            .performTextInput(searchQuery)

        composeTestRule.onAllNodesWithTag("articles_list").fetchSemanticsNodes().isNotEmpty()
        // Click on the first news item
        composeTestRule.onNodeWithTag("articles_list")
            .onChildAt(0)
            .performClick()

        // Verify that the detail screen is displayed
        composeTestRule.onNodeWithTag("detail_screen").assertExists()
    }

    @Test
    fun detailScreen_clickBack_navigateToHomeScreen() = runTest {
        composeTestRule.onNodeWithTag("articles_list").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("articles_list").fetchSemanticsNodes().isNotEmpty()

        // Navigate to detail screen first
        composeTestRule.onNodeWithTag("articles_list")
            .onChildAt(0)
            .performClick()

        // Verify that the detail screen is displayed
        composeTestRule.onNodeWithTag("detail_screen").assertExists()

        // Click on the back button
        composeTestRule.onNodeWithTag("back_button")
            .performClick()

        // Verify that the home screen is displayed again
        composeTestRule.onNodeWithTag("articles_list").assertIsDisplayed()
    }

}