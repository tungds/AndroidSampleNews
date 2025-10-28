package com.example.samplearchitecture.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplearchitecture.data.Article
import com.example.samplearchitecture.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter

data class NewsHomeUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class NewsHomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel(){

    private val _uiState = MutableStateFlow(NewsHomeUiState())
    val uiState: StateFlow<NewsHomeUiState> = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set
    private var listArticle = listOf<Article>()

    init {
        fetchNews()
    }

    fun fetchNews(){
        // Implement fetch news logic here
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                listArticle = newsRepository.getArticles()
                _uiState.update { it.copy(isLoading = false, articles = listArticle.filteredArticles(searchQuery)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun searchNews(query: String) {
        searchQuery = query
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val filteredArticles = listArticle.filteredArticles(query)
                _uiState.update { it.copy(isLoading = false, articles = filteredArticles) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun List<Article>.filteredArticles(query: String): List<Article> {
        return this.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.description?.contains(query, ignoreCase = true)?:false
        }
    }
}