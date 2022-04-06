package com.company.strengthtracker.data.repository

import com.company.strengthtracker.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

// https://youtu.be/bekW_W3A2EY?t=250
// Class responsible for:
// Firebase Sign-in and Sign-out operations, both async
// Checking authentication
// Actual implementation of AuthRepository interface
@ExperimentalCoroutinesApi
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {
    override fun isUserAuthenticatedInFirebase(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFirebaseAuthState(): Flow<Boolean> {
        TODO("Not yet implemented")
    }


}