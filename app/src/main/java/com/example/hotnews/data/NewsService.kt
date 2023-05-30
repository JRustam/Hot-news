package com.example.hotnews.data

import retrofit2.http.GET

interface NewsService {

    @GET("")
    suspend fun getTopHeadlines()
}