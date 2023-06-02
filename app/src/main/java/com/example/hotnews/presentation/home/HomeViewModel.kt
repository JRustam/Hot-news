package com.example.hotnews.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotnews.BuildConfig
import com.example.hotnews.data.network.response.Articles
import com.example.hotnews.presentation.BaseViewModel
import com.example.hotnews.presentation.repository.NewsRepository
import com.example.hotnews.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
    application: Application
) : BaseViewModel(application = application) {

    private val _articlesState = MutableStateFlow(emptyList<Articles>())
    val articles = _articlesState
    private val _isLoadingState = MutableStateFlow(true)
    val isLoading = _isLoadingState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _isLoadingState.value = false

        handleException(throwable)
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            val news = repository.getTopHeadlines(
                country = application.resources.configuration.locales[0].country, apiKey = BuildConfig.API_KEY)

            if (news is Result.Success && news.data != null) {
                _articlesState.value = news.data.articles
                _isLoadingState.value = false
            }
        }
    }
}