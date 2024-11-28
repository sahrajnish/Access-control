package com.example.accesscontrol.feature_app.domain.use_case

import com.example.accesscontrol.feature_app.domain.model.AppUser
import com.example.accesscontrol.feature_app.domain.repository.UserRepository

class GetUser(private val repository: UserRepository) {
    suspend operator fun invoke(userId: String): AppUser? {
        return repository.getUser(userId)
    }
}