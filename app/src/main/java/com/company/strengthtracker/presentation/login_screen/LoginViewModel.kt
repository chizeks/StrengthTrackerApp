package com.company.strengthtracker.presentation.login_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var isLoggedIn = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        isLoading.value = true
        viewModelScope.launch {
            val result = authRepositoryImpl.getCurrentUser()
            println("here result is ${result?.uid}")
            isLoading.value = false
            if (result == null)
                isLoggedIn.value = false
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
            println("here ${response.message}")
            println("here ${response.data}")
            when (response) {
                is Resource.Success -> {
                    isLoading.value = false
                    isLoggedIn.value = true
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