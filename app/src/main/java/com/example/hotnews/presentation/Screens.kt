package com.example.hotnews.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screens(val route: String, val icon: ImageVector) {
    HOME(route = "Home", icon = Icons.Default.Home),
    SEARCH(route = "Search", icon = Icons.Default.Search)
}