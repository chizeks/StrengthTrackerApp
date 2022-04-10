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

    // Possible states that this screen should take
    enum class WelcomeScreenState {
        LAUNCH, LOADING, CONNECTED, ERROR, DISCONNECTED
    }

    // Referenced in WelcomeScreen to determine which UI should be presented to user
    var welcomeScreenState = mutableStateOf(WelcomeScreenState.LAUNCH)

    // Equal to null if not currently logged in
    var currentFirebaseUser: MutableState<FirebaseUser?> = mutableStateOf(null)
    var currentUser: MutableState<User?> = mutableStateOf(null)

    fun getCurrentUserFromCollections() {

        welcomeScreenState.value = WelcomeScreenState.LOADING

        viewModelScope.launch {

            currentFirebaseUser.value = authRepositoryImpl.getCurrentUser()
            val response = authRepositoryImpl
                .getUserDataFromFirestore(currentFirebaseUser.value!!.uid)

            println("Here $response")
           welcomeScreenState.value = when (response) {
                is Resource.Success -> {
                    currentUser.value = response.data
                    WelcomeScreenState.CONNECTED
                }
                is Resource.Error -> {
                    WelcomeScreenState.ERROR
                }
            }


        }
    }

    // Invoked on an already logged-in user to log-out; may remove later
    fun logout() {
        welcomeScreenState.value = WelcomeScreenState.LOADING
        viewModelScope.launch {
            authRepositoryImpl.logout()
            currentUser.value = null
            welcomeScreenState.value = WelcomeScreenState.DISCONNECTED
        }
    }
}