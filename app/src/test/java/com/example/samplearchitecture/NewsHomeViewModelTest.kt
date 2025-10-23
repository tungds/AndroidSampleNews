package com.example.samplearchitecture

import com.example.samplearchitecture.data.Article
import com.example.samplearchitecture.data.repository.NewsRepository
import com.example.samplearchitecture.ui.home.NewsHomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NewsHomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val newsRepository = mockk<NewsRepository>()
    private lateinit var newsHomeViewModel: NewsHomeViewModel

    @Before
    fun setup() {
        coEvery { newsRepository.getArticles() } coAnswers {
            delay(100)
            listOf(
                Article("Title", "Desc", null, null, null, "2025-10-22")
            )
        }

    }

    @Test
    fun loadArticles_loading() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        newsHomeViewModel = NewsHomeViewModel(newsRepository)
        newsHomeViewModel.fetchNews()

        advanceTimeBy(50)
        assertTrue(newsHomeViewModel.uiState.value.isLoading)

        advanceUntilIdle()
        assertFalse(newsHomeViewModel.uiState.value.isLoading)
    }

    @Test
    fun `fetchNews loads data successfully`() = runTest {
        newsHomeViewModel = NewsHomeViewModel(newsRepository)
        newsHomeViewModel.fetchNews()

        advanceUntilIdle()

        val state = newsHomeViewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.articles.size)
    }
}