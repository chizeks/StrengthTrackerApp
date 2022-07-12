package com.company.strengthtracker.presentation.progress_screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import java.time.LocalDate
import javax.inject.Inject

class ProgressViewModel @Inject constructor(
private val logRepository: SetRepositoryImpl

) : ViewModel() {
    val input = mutableMapOf(
        LocalDate.of(2022, 4, 15) to 5,
        LocalDate.of(2022, 4, 17) to 4,
        LocalDate.of(2022, 4, 19) to 4,
        LocalDate.of(2022, 4, 21) to 4,
        LocalDate.of(2022, 4, 23) to 4
    )
    val sortedInp = input.toSortedMap()


    class Colors() {
        val primary: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.primary

        val onPrimary: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onPrimary

        val secondary: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.secondary

        val onSecondary: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onSecondary

        val surface: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.surface

        val onSurface: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.onSurface

    }
}

