package com.example.accesscontrol.feature_app.presentation.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.accesscontrol.feature_app.presentation.util.Screens

@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.3f)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Home screen",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.7f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.user != null -> Text(text = "Welcome, ${state.user.email}")
                state.error != null -> Text(text = "Error: ${state.error}")
                else -> Text(text = "Loading email...")
            }
            Spacer(Modifier.height(8.dp))

            when {
                state.user != null -> Text(text = "User type: ${state.user.userType}")
                state.error != null -> Text(text = "Error: ${state.error}")
                else -> Text(text = "Loading user type...")
            }
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.onEvent(MainEvents.SignOut)
                    val error = viewModel.state.value.error
                    if (error == null) {
                        navController.navigate(Screens.WelcomeScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign out"
                )
            }
        }
    }
}