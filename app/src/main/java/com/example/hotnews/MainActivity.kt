package com.example.hotnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.hotnews.presentation.BottomBar
import com.example.hotnews.presentation.Screens
import com.example.hotnews.presentation.navigation.Navigation
import com.example.hotnews.presentation.ui.theme.HotNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotNewsTheme {
                val navController = rememberNavController()
                Scaffold(
                    content = { padding ->
                        Navigation(modifier = Modifier.padding(padding), navController = navController)
                    },
                    bottomBar = {
                    BottomBar(navController = navController,
                        items = listOf(
                            Screens.HOME,
                            Screens.SEARCH
                        ) ,
                        onItemClick = {
                        navController.navigate(it.route)
                    }
                    )
                },

               )
            }
        }
    }
}