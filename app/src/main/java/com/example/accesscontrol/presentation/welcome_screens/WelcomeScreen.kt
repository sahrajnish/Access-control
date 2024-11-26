package com.example.accesscontrol.presentation.welcome_screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.accesscontrol.presentation.util.Screens
import com.example.accesscontrol.presentation.welcome_screens.components.WelcomeScreenButton

@Composable
fun WelcomeScreen(
    navHostController: NavHostController
) {
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