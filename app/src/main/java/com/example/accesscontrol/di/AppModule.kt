package com.example.accesscontrol.di

import com.example.accesscontrol.feature_app.data.repository.UserRepositoryImp
import com.example.accesscontrol.feature_app.domain.repository.UserRepository
import com.example.accesscontrol.feature_app.domain.use_case.AddUser
import com.example.accesscontrol.feature_app.domain.use_case.AppUseCases
import com.example.accesscontrol.feature_app.domain.use_case.GetUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository {
        return UserRepositoryImp(firestore)
    }

    @Provides
    @Singleton
    fun provideAppUseCases(repository: UserRepository): AppUseCases {
        return AppUseCases(
            addUser = AddUser(repository),
            getUser = GetUser(repository)
        )
    }
}