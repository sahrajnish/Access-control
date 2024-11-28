package com.example.accesscontrol.feature_app.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accesscontrol.feature_app.presentation.main_screens.HomeScreen
import com.example.accesscontrol.feature_app.presentation.util.Screens
import com.example.accesscontrol.feature_app.presentation.starting_screens.login_screen.LoginScreen
import com.example.accesscontrol.feature_app.presentation.starting_screens.register_screen.RegisterScreen
import com.example.accesscontrol.feature_app.presentation.starting_screens.welcome_screen.WelcomeScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun NavigateScreens(
    navHostController: NavHostController,
    currentUser: FirebaseUser?
) {
    NavHost(
        navController = navHostController,
        startDestination = if (currentUser != null) {
            Screens.HomeScreen.route
        } else {
            Screens.WelcomeScreen.route
        },
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