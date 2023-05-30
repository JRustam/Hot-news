package com.example.hotnews.di

import com.example.hotnews.presentation.repository.NewsRepository
import com.example.hotnews.presentation.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesNewsRepository(repositoryImpl: NewsRepositoryImpl) : NewsRepository = repositoryImpl
}