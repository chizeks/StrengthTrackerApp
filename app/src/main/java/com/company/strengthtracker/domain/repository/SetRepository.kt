package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.auth.FirebaseUser
import java.time.LocalDate

interface SetRepository {

    suspend fun addStaticSet(
        name: String,
        progression: String,
        time: String,
        assistance: String,
        SiR: String,
        date: LocalDate
    ): Resource<String>
}