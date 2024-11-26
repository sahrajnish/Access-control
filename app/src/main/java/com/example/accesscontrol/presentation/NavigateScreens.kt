package com.example.accesscontrol.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accesscontrol.presentation.util.Screens
import com.example.accesscontrol.presentation.welcome_screens.LoginScreen
import com.example.accesscontrol.presentation.welcome_screens.RegisterScreen
import com.example.accesscontrol.presentation.welcome_screens.WelcomeScreen

@Composable
fun NavigateScreens(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.WelcomeScreen.route,
        enterTransition = {
            fadeIn(animationSpec = tween(10))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(10))
        }
    ) {
        composable(route = Screens.WelcomeScreen.route) {
            WelcomeScreen(navHostController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navHostController)
        }
        composable(route = Screens.RegisterScreen.route) {
            RegisterScreen(navController = navHostController)
        }
        composable(
            route = Screens.HomeScreen.route
        ) {
            HomeScreen(
                navController = navHostController
            )
        }
    }
}