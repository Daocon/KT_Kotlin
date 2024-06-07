package com.daco.kt_kotlin.presentation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splashScreen")
    object HomeScreen : Screen("homeScreen")
}