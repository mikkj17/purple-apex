package com.example.purpleapex.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purpleapex.news.domain.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: NewsListAction) {
        when (action) {
            is NewsListAction.OnArticleClick -> {
                _state.update {
                    it.copy(selectedArticle = action.article)
                }
            }

            is NewsListAction.OnArticleClose -> {
                _state.update {
                    it.copy(selectedArticle = null)
                }
            }

            is NewsListAction.OnRetryClick -> {
                load()
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val articles = repository.getLatest()
                _state.update { it.copy(isLoading = false, articles = articles) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message ?: "Unknown error") }
            }
        }
    }
}
