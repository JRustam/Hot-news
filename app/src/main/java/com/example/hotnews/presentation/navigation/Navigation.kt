package com.example.hotnews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hotnews.presentation.Screens
import com.example.hotnews.presentation.home.HomeScreen

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screens.HOME.route) {
        composable(route = Screens.HOME.route) {
            HomeScreen(modifier = modifier)
        }
    }
}