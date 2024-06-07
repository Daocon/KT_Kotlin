package com.daco.kt_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daco.kt_kotlin.presentation.Screen
import com.daco.kt_kotlin.presentation.screens.HomeScreen
import com.daco.kt_kotlin.presentation.screens.HomeViewModel
import com.daco.kt_kotlin.presentation.screens.SplashScreen
import com.daco.kt_kotlin.ui.theme.KT_KotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ) {
                composable(Screen.SplashScreen.route) {
                    SplashScreen(navController)
                }
                composable(Screen.HomeScreen.route) {
                    val homeViewModel: HomeViewModel = viewModel()
                    HomeScreen(homeViewModel)
                }
            }
        }
    }
}
