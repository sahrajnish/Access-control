package com.example.accesscontrol.feature_app.data.repository

import com.example.accesscontrol.feature_app.domain.model.AppUser
import com.example.accesscontrol.feature_app.domain.repository.UserRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImp(
    firestore: FirebaseFirestore
): UserRepository {

    private val userCollection: CollectionReference = firestore.collection("Users")

    override suspend fun insertUser(user: AppUser) {
        val documentId = user.id ?: userCollection.document().id
        userCollection.document(documentId).set(user)
            .addOnSuccessListener {
                println("User inserted successfully.")
            }
            .addOnFailureListener {e ->
                println("Failed to insert user: ${e.message}")
            }
    }

    override suspend fun getUser(id: String): AppUser? {
        val snapshot = userCollection.document(id).get().await()
        println("Firestore document: ${snapshot.data}")
        return snapshot.toObject(AppUser::class.java)
    }
}