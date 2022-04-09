package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun registerUser(
        email: String,
        password: String,
        username: String
    ): Resource<Boolean>

    suspend fun login(
        email: String,
        password: String
    ): Resource<FirebaseUser?>

    suspend fun logout(): Resource<Boolean>

    suspend fun getUserDataFromFirestore(
        uid: String
    ): Resource<User?>

}