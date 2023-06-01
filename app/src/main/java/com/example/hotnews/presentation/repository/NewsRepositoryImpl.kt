package com.example.hotnews.presentation.repository

import com.example.hotnews.data.NewsService
import com.example.hotnews.data.network.response.News
import com.example.hotnews.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO,
    private val newsService: NewsService
) :  NewsRepository {

    override suspend fun getTopHeadlines(country: String, apiKey: String): Result<News?> {
       return withContext(dispatchers) {
           val news = newsService.getTopHeadlines(country = country, apiKey = apiKey)

           if (news.isSuccessful) {
               return@withContext Result.Success(news.body())
           } else {
               return@withContext Result.Error(Exception())
           }
       }
    }
}