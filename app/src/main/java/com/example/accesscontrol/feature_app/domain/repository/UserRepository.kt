package com.example.accesscontrol.feature_app.domain.repository

import com.example.accesscontrol.feature_app.domain.model.AppUser

interface UserRepository {
    suspend fun insertUser(user: AppUser)

    suspend fun getUser(id: String): AppUser?
}