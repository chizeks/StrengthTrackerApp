package com.company.strengthtracker.data.repository

import com.company.strengthtracker.domain.repository.AuthRepository
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

//class SetRepositoryImpl @Inject constructor(
//    private val auth: FirebaseAuth // Implementation of Authentication
//) : SetRepository {
//
//
//
////    override suspend fun addStaticSet(
////        name: String,
////        progression: String,
////        time: String,
////        assistance: String,
////        SiR: String,
////        date: LocalDate
////    ) = try {
////        // Method provided by FirebaseAuth
////        val response = auth.createUserWithEmailAndPassword(
//////            email,
//////            password
////        ).await()
////        if (response.user != null)
////            Resource.Success(response.user!!.uid)
////        else Resource.Error("An error occurred in registration.")
////
////    } catch (e: Exception) {
////        Resource.Error("${e.message}")
////    }
//
//}