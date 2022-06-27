package com.company.strengthtracker.domain.repository

import android.content.ContentValues
import android.util.Log
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.domain.util.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.time.LocalDate

interface SetRepository {
    suspend fun setUpdater(
        exerciseBundle:MutableList<MutableList<AllExercises>>,
        date: String,
        userUid: String
    ):Resource<MutableList<MutableList<AllExercises>>>


    suspend fun addSet(movement:AllExercises):Resource<String>

}