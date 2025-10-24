package com.example.samplearchitecture.ui.home

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

    init {
        fetchNews()
    }

    fun fetchNews(){
        // Implement fetch news logic here
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val articles = newsRepository.getArticles()
                _uiState.update { it.copy(isLoading = false, articles = articles) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

}