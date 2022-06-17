package com.company.strengthtracker.presentation.template_day_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.company.strengthtracker.presentation.register_screen.RegisterViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {
    val db = Firebase.firestore




    fun addStaticsSet(
        name: String,
        progression: String,
        time: String,
        assistance: String,
        SiR: String,
        date: LocalDate
    ) {
        val set = hashMapOf(
            "setNumber" to "set1", //PLACEHOLDER
            "date" to date.toString(),
            "name" to name,
            "progression" to progression,
            "time" to time,
            "assistance" to assistance,
            "SiR" to SiR,
            )
        db.collection("userID").document(date.toString())
            .collection(name).add(set)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }


    }

//    fun registerUser(
//        email: String,
//        password: String,
//        username: String
//    ) {
//        registerScreenState.value = RegisterViewModel.RegisterScreenState.LOADING
//
//        viewModelScope.launch {
//            val response = authRepositoryImpl.registerUser(email, password)
//            if (response is Resource.Success)
//                usersRepositoryImpl.createUser(
//                    User(
//                        uid = response.data,
//                        email = email,
//                        username = username
//                    )
//                )
//
//            registerScreenState.value = when (response) {
//                is Resource.Success -> RegisterViewModel.RegisterScreenState.REGISTER_SUCCESS
//                is Resource.Error -> RegisterViewModel.RegisterScreenState.REGISTER_ERROR
//            }
//        }
//    }
}