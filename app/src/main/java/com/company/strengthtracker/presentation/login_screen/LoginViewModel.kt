package com.company.strengthtracker.presentation.login_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    // Equal to null if not currently logged in
    var currentUser: MutableState<FirebaseUser?> = mutableStateOf(null)

    init {
        // First thing: check to see if user is already logged in
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        isLoading.value = true
        viewModelScope.launch {
            currentUser.value  = authRepositoryImpl.getCurrentUser()
            isLoading.value = false
        }
    }


    fun logout() {
        isLoading.value = true
        viewModelScope.launch {
            authRepositoryImpl.logout()
            currentUser.value  = authRepositoryImpl.getCurrentUser()
            isLoading.value = false
            endReached.value = false
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        isLoading.value = true

        viewModelScope.launch {
            val response = authRepositoryImpl.login(
                email = email,
                password = password
            )

            // Update current user
            currentUser.value = response.data
            when (response) {
                is Resource.Success -> {
                    isLoading.value = false
                    endReached.value = true
                }
                is Resource.Error -> {
                    isLoading.value = false
                    endReached.value = true
                }
            }
        }
    }
}