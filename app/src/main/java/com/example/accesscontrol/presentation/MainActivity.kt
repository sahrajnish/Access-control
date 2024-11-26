package com.example.accesscontrol.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.accesscontrol.presentation.util.Screens
import com.example.accesscontrol.presentation.welcome_screens.LoginScreen
import com.example.accesscontrol.presentation.welcome_screens.WelcomeScreen
import com.example.accesscontrol.ui.theme.AccessControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccessControlTheme {
                val navController: NavHostController = rememberNavController()
                NavigateScreens(navController)
            }
        }
    }
}