package com.example.hotnews.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    NewsList()
}

@Composable
fun NewsList() {
    LazyColumn(modifier = Modifier.padding(10.dp)) {

    }
}