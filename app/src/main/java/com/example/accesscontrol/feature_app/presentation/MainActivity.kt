package com.example.accesscontrol.feature_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.accesscontrol.ui.theme.AccessControlTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccessControlTheme {
                val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                val navController: NavHostController = rememberNavController()
                NavigateScreens(
                    navHostController = navController,
                    currentUser = currentUser
                )
            }
        }
    }
}