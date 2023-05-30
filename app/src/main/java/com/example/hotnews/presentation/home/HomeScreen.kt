package com.example.hotnews.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hotnews.data.network.response.Articles
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hotnews.BuildConfig

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    viewModel.fetchTopHeadlines(BuildConfig.API_KEY)

    NewsList(viewModel.articles.collectAsState().value)
}

@Composable
fun NewsList(articles: List<Articles>) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(articles) {

        }
    }
}