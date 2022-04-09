package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.Binds
import dagger.Provides
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

interface AuthRepository {

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun registerUser(
        email: String,
        password: String
    ): Resource<Boolean>

    suspend fun login(
        email: String,
        password: String
    ): Resource<FirebaseUser?>

    suspend fun logout(): Resource<Boolean>

}