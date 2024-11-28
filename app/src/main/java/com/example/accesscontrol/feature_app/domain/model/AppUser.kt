package com.example.accesscontrol.feature_app.domain.model

data class AppUser(
    val id: String? = null,
    val email: String? = null,
    val userType: UserType = UserType.Standard
)