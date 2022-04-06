package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean
  //  suspend fun firebaseSignInAnonymously(): Flow<Response<Boolean>>
  //  suspend fun signOut(): Flow<Response<Boolean>>
    fun getFirebaseAuthState(): Flow<Boolean>
}