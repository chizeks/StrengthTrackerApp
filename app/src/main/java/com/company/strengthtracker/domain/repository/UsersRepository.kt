package com.company.strengthtracker.domain.repository

import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.domain.util.Resource
import java.time.LocalDate

interface UsersRepository {
    suspend fun setUpdater(
        exerciseBundle:MutableList<MutableList<AllExercises>>,
        date: String,
        userUid: String
    ):Resource<MutableList<MutableList<AllExercises>>>


    suspend fun getUserByUid(
        uid: String
    ): Resource<User?>

    suspend fun createUser(
        user: User
    )

    suspend fun updateUserUsernameByUid(
        uid: String,
        username: String
    )

    suspend fun deleteUserByUid(
        uid: String
    )

}