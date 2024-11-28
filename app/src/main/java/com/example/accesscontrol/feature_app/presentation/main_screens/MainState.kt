package com.example.accesscontrol.feature_app.presentation.main_screens

import com.example.accesscontrol.feature_app.domain.model.AppUser

data class MainState(
    val user: AppUser? = null,
    val error: String? = null,
    val message: String? = null
)