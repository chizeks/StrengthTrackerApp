package com.company.strengthtracker.presentation.template_day_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {


}