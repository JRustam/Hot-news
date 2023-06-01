package com.example.hotnews.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    items: List<Screens>,
    modifier: Modifier = Modifier,
    onItemClick: (Screens) -> Unit) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.DarkGray,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == navController.currentDestination?.route,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = null)
                }
            )
        }
    }
}