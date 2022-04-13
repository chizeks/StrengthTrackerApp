package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun registerUser(
        email: String,
        password: String
    ): Resource<String>

    suspend fun login(
        email: String,
        password: String
    ): Resource<FirebaseUser?>

    suspend fun logout(): Resource<Boolean>

}