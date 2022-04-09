package com.company.strengthtracker.presentation.login_screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
//import com.company.strengthtracker.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    // Are we currently making an active async call?
    var isLoading = mutableStateOf(false)
    // Are we at the end-state of an async call?
    var endReached = mutableStateOf(false)
    var message = mutableStateOf("")
    var hasError = mutableStateOf(false)

    fun registerUser(
        email: String,
        password: String
    ) {
        isLoading.value = true

        viewModelScope.launch {
            val response = authRepositoryImpl.registerUser(email, password)
            when (response) {
                is Resource.Success -> {
                    isLoading.value = false
                    endReached.value = true
                    hasError.value = false
                }
                is Resource.Error -> {
                    isLoading.value = false
                    endReached.value = true
                    hasError.value = true
                }
            }
        }
    }

    fun reset() {
        isLoading.value = false
        endReached.value = false
        hasError.value = false
    }
}