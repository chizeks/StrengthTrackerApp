package com.company.strengthtracker.data.repository

import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.domain.repository.AuthRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

// https://youtu.be/bekW_W3A2EY?t=250
// Class responsible for:
// Firebase Sign-in and Sign-out operations, both async
// Checking authentication
// Actual implementation of AuthRepository interface

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth, // Implementation of Authentication
    private val usersCollection: CollectionReference
) : AuthRepository {

    override suspend fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String
    ): Resource<Boolean> {
        return try {
            val response = auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()

            if (response.user != null) {
                // User has been added to Firebase Authentication, add to Firestore collection now
                createUserInCollection(
                    user = User(
                        uid = response!!.user!!.uid,
                        email = email,
                        username = username
                    )
                )
                Resource.Success(true)
            }
            else Resource.Error("some error")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val response = auth.signInWithEmailAndPassword(
                email,
                password
            ).await()
            if (response.user != null) Resource.Success(response.user)
            else Resource.Error("Error in login")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun logout(): Resource<Boolean> {
        return try {
            auth.signOut()
            return Resource.Success(true)
        }  catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun getUserDataFromFirestore(uid: String): Resource<User?> {
        return try {
            val response = usersCollection.whereEqualTo(
                "uid",
                uid
            ).get().await()
            if (response != null && !response.isEmpty) {
                for (document in response.documents) {
                    val user = document.toObject(User::class.java)
                    return Resource.Success(data = user)
                }
                return Resource.Error("Error in login")
            }
            else Resource.Error("Error in login")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    private suspend fun createUserInCollection(user: User) {
        try {
            val response = usersCollection.add(user).await()
            println("Created user of id ${response.id}")
        } catch (e: Exception) {
            println("Error occurred creating user in collection")
        }
    }
}