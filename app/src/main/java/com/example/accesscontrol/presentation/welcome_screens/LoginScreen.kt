package com.example.accesscontrol.presentation.welcome_screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.accesscontrol.presentation.util.Screens
import com.example.accesscontrol.presentation.welcome_screens.components.AppTextField

@Composable
fun LoginScreen(
    viewModel: WelcomeScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val loginState = viewModel.state.value.isLoginSuccess
    LaunchedEffect(loginState) {
        if (loginState) {
            navController.navigate(Screens.HomeScreen.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "It's good to see you back",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Account Information",
                style = MaterialTheme.typography.labelMedium
            )
            AppTextField(
                value = viewModel.state.value.email,
                label = "Email",
                onValueChange = {
                    viewModel.onEvent(WelcomeScreensEvents.EnteredEmail(it))
                }
            )
            AppTextField(
                value = viewModel.state.value.password,
                label = "Password",
                onValueChange = {
                    viewModel.onEvent(WelcomeScreensEvents.EnteredPassword(it))
                },
                keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onDone),
                transformText = PasswordVisualTransformation()
            )
            TextButton(
                onClick = {
                    viewModel.onEvent(WelcomeScreensEvents.ResetPassword)
                }
            ) {
                Text(
                    text = "Forgot your password?"
                )
            }
            Button(
                onClick = {
                    viewModel.onEvent(WelcomeScreensEvents.LoginClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = if (!viewModel.state.value.isLoading) {
                        Arrangement.Center
                    } else { Arrangement.SpaceBetween },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Log in",
                        fontSize = 16.sp
                    )
                    if (viewModel.state.value.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(16.dp),
                            color = ProgressIndicatorDefaults.linearTrackColor
                        )
                    }
                }
            }

            if (!viewModel.state.value.error.isNullOrBlank()) {
                Text(
                    text = viewModel.state.value.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else if (!viewModel.state.value.message.isNullOrBlank()) {
                Text(
                    text = viewModel.state.value.message!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}