package com.example.hotnews.data.network.response

import com.google.gson.annotations.SerializedName

data class News (
    @SerializedName("articles")
    val articles: List<Articles>
)

data class Articles (
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("urlToImage")
    val urlToImage: String?
)