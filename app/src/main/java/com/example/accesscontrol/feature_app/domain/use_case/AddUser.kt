package com.example.accesscontrol.feature_app.domain.use_case

import com.example.accesscontrol.feature_app.domain.model.AppUser
import com.example.accesscontrol.feature_app.domain.repository.UserRepository

class AddUser(private val repository: UserRepository) {
    suspend operator fun invoke(user: AppUser) {
        repository.insertUser(user)
    }
}