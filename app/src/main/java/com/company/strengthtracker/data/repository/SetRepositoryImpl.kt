package com.company.strengthtracker.data.repository

import android.content.ContentValues
import android.util.Log
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.domain.repository.AuthRepository
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class SetRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth // Implementation of Authentication
) : SetRepository {


    val db = Firebase.firestore
    override suspend fun setUpdater(date: String
    ): Task<QuerySnapshot> {
        var exerciseBundle: MutableList<AllExercises> = mutableListOf()
        val docRef = db.collection("test").document(
            date.toString()
        ).collection(date.toString())
        return docRef.get()

        docRef.get().addOnSuccessListener { sets ->
            Log.d(ContentValues.TAG, "SIZE = ${sets.size()}")
            for (set in sets) {
                if (set.get("exType") == "static") {
                    Log.d(ContentValues.TAG, "Retrieving exercise ---> ${set.id} => ${set.data}")
                    exerciseBundle.add(
                        Statics(
                            name = set.get("name") as String,
                            holdTime = set.get("holdTime") as String,
                            weight = set.get("weight") as String,
                            sir = set.get("sir") as String,
                            progression = set.get("progression") as String,
                            setNumber = set.get("setNumber") as Long,
                            notes = set.get("notes") as String
                        )
                    )
                    Log.d(ContentValues.TAG, "FUCK ---> 0")
                    Log.d(ContentValues.TAG, "WEIGHT ---->${exerciseBundle.get(exerciseBundle.size - 1).weight}")
                }
                if (set.get("exType") == "dynamic") {

                }
            }
            Log.d(ContentValues.TAG, "assigning bundle ${exerciseBundle.size}")

            //Log.d(ContentValues.TAG, "assigned bundle ${exerciseBundleMain.size}")

        }

        //Log.d(ContentValues.TAG, "RETURN ${exerciseBundle.size}")

    }
}