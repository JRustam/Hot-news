package com.example.hotnews.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotnews.BuildConfig
import com.example.hotnews.data.network.response.Articles
import com.example.hotnews.presentation.repository.NewsRepository
import com.example.hotnews.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _articlesState = MutableStateFlow(emptyList<Articles>())
    val articles = _articlesState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->

    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val news = repository.getTopHeadlines(country = "us", apiKey = BuildConfig.API_KEY)

            if (news is Result.Success && news.data != null) {
                _articlesState.value = news.data.articles
            }
        }
    }

}