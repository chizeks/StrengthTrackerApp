package com.company.strengthtracker.presentation.exercise_select_screen.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.company.strengthtracker.data.entities.User
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




}