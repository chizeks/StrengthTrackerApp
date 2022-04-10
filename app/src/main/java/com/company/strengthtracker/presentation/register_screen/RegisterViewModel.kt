package com.company.strengthtracker.presentation.register_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    // Possible states that this screen should take
    enum class RegisterScreenState {
        LAUNCH, LOADING, REGISTER_SUCCESS, REGISTER_ERROR
    }

    // Referenced in screen to determine which UI should be presented to user
    var registerScreenState = mutableStateOf(RegisterScreenState.LAUNCH)

    fun registerUser(
        email: String,
        password: String,
        username: String
    ) {
        registerScreenState.value = RegisterScreenState.LOADING

        viewModelScope.launch {
            val response = authRepositoryImpl.registerUser(email, password, username)
            registerScreenState.value = when (response) {
                is Resource.Success -> RegisterScreenState.REGISTER_SUCCESS
                is Resource.Error -> RegisterScreenState.REGISTER_ERROR
            }
        }
    }

    fun reset() {
        registerScreenState.value = RegisterScreenState.LAUNCH
    }
}