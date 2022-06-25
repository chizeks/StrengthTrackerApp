package com.company.strengthtracker.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.domain.repository.UsersRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersCollection: CollectionReference,
    private val db: FirebaseFirestore = Firebase.firestore
) : UsersRepository {

    override suspend fun setUpdater(
        exerciseBundle: MutableList<MutableList<AllExercises>>,
        date: String,
        userUid: String
    ): Resource<MutableList<MutableList<AllExercises>>> {
        //Creates or finds subcollection under exercise name
        val setBundle: MutableList<AllExercises> = mutableListOf()
        val allExercisesRef = db.collection(userUid)
            .document(date)
            .collection(date).get().await()
        Log.d(TAG, "awaited  ex fetch and Size is {${allExercisesRef.documents.size}}")
        allExercisesRef?.documents?.forEach { exercise ->
            Log.d(TAG, "${exercise.get("name").toString()}")
            if (exercise != null) {
                val exName = exercise.get("name").toString()
                val exType = exercise.get("exType").toString()
                val setRef = db.collection(userUid)
                    .document(date)
                    .collection(date)
                    .document(exName)
                    .collection(exName).get().await()

                Log.d(TAG, "awaited set  fetch and size is ${setRef.documents.size}")
                setRef?.documents?.forEach { set ->
                    Log.d(TAG, "${set.get("name").toString()}")
                    if (set != null) {
                        if (exType == "STATIC") {
                            setBundle.add(
                                Statics(
                                    name = set.get("name") as String,
                                    holdTime = set.get("holdTime") as String,
                                    weight = set.get("weight") as String,
                                    sir = set.get("sir") as String,
                                    progression = set.get("progression") as String,
                                    setNumber = set.get("setNumber") as Long,
                                    iconId = -1
                                )
                            )
                            Log.d(TAG, "ADDING STATIC SET")
                        }
                        if (exType == "DYNAMIC") {
                            setBundle.add(
                                Dynamics(
                                    name = set.get("name") as String,
                                    weight = set.get("weight") as String,
                                    reps = set.get("reps") as String,
                                    rir = set.get("rir") as String,
                                    setNumber = set.get("setNumber") as Long,
                                    iconId = -1
                                )
                            )
                            Log.d(TAG, "ADDING DYNAMIC SET")
                        }
                    } else {

                    }
                }
                if (exerciseBundle.size == 1 && exerciseBundle[0][0].name == setBundle[0].name
                ) {
                    Log.d(TAG, "Adding set bundle as replacement " + setBundle.size)
                    exerciseBundle[0] = setBundle
                } else {
                    Log.d(TAG, "Adding set bundle as addition " + setBundle.size)
                    exerciseBundle.add(setBundle)
                }
            }
        }
        Log.d(TAG, "RETURNING RESOURCE SUCCESS exBundle size ---> ${exerciseBundle.size}")
        return Resource.Success(exerciseBundle)
    }

    override suspend fun getUserByUid(uid: String): Resource<User?> {
        return try {
            val response = usersCollection.document(uid).get().await()
            if (response != null) {
                val user = response.toObject(User::class.java)
                return Resource.Success(data = user)
            } else Resource.Error("Error in login")

        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun createUser(user: User) {
        usersCollection.document(user.uid).set(user).await()
    }

    override suspend fun updateUserUsernameByUid(uid: String, username: String) {
        // https://firebase.google.com/docs/firestore/manage-data/add-data#kotlin+ktx
        val data = hashMapOf("username" to username)
        usersCollection.document(uid).update(data as Map<String, Any>).await()
    }

    override suspend fun deleteUserByUid(uid: String) {
        usersCollection.document(uid).delete()
    }
}