package com.example.hotnews.presentation.repository

import com.example.hotnews.data.network.response.News
import com.example.hotnews.utils.Result

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, apiKey: String) : Result<News?>
}