package com.example.hotnews.di

import com.example.hotnews.data.NewsService
import com.example.hotnews.presentation.repository.NewsRepository
import com.example.hotnews.presentation.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesNewsRepository(newsService: NewsService): NewsRepository =
        NewsRepositoryImpl(newsService = newsService, dispatchers = Dispatchers.IO)
}