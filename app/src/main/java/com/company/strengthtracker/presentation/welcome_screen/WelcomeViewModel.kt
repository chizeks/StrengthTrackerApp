package com.company.strengthtracker.presentation.welcome_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var hasError = mutableStateOf(false)

    // Equal to null if not currently logged in
    var currentFirebaseUser: MutableState<FirebaseUser?> = mutableStateOf(null)
    var currentUser: MutableState<User?> = mutableStateOf(null)

    init {
        // First thing: check to see if user is already logged in
        getCurrentUserFromCollections()
    }

    private fun getCurrentUserFromCollections() {
        isLoading.value = true
        viewModelScope.launch {
            currentFirebaseUser.value = authRepositoryImpl.getCurrentUser()
            val response = authRepositoryImpl
                .getUserDataFromFirestore(currentFirebaseUser.value!!.uid)
            when (response) {
                is Resource.Success -> {
                    currentUser.value = response.data
                    endReached.value = true
                }
                is Resource.Error -> {
                    hasError.value = true
                    endReached.value = true
                }
            }
            isLoading.value = false
        }
    }
}