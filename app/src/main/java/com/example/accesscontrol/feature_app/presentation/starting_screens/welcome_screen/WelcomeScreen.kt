package com.example.accesscontrol.feature_app.presentation.starting_screens.welcome_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.accesscontrol.feature_app.presentation.util.Screens
import com.example.accesscontrol.feature_app.presentation.starting_screens.components.WelcomeScreenButton

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: WelcomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(state.isLoginSuccess) {
        Log.d("inspect", "Inside welcome screen")
        if (state.isLoginSuccess) {
            navHostController.navigate(Screens.HomeScreen.route) {
                popUpTo(Screens.WelcomeScreen.route) { inclusive = true }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        WelcomeScreenButton(
            buttonText = "Register",
            onButtonClick = {
                navHostController.navigate(Screens.RegisterScreen.route)
            }
        )

        WelcomeScreenButton(
            buttonText = "Login",
            onButtonClick = {
                navHostController.navigate(Screens.LoginScreen.route)
            }
        )
    }
}