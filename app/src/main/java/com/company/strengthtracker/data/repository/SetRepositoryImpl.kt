package com.company.strengthtracker.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.domain.repository.AuthRepository
import com.company.strengthtracker.domain.repository.SetRepository
import com.company.strengthtracker.domain.util.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.nio.file.Path
import java.time.LocalDate
import javax.inject.Inject
//
class SetRepositoryImpl @Inject constructor(

) : SetRepository {
    private val db:FirebaseFirestore = Firebase.firestore
    override suspend fun setUpdater(
        exerciseBundle: MutableList<MutableList<AllExercises>>,
        date: String,
        userUid: String
    ): Resource<MutableList<MutableList<AllExercises>>> {
        val setBundle: MutableList<AllExercises> = mutableListOf() //holds lists to be added to the main bundle

        //reference to collection of exercises logged by the user on a specified date
        //.await() is used to wait for the async function
        val allExercisesRef = db.collection(userUid)
            .document(date)
            .collection(date).get().await()

        //foreach on the documents within allExercisesRef subcollection
        allExercisesRef?.documents?.forEach { exercise ->
            if (exercise != null) {
                val exName = exercise.get("name").toString() //holds name field of subcollection
                val exType = exercise.get("exType").toString() //holds type of exercise for object construction

                //reference to subcollection of sets for a specific exercise logged by the user on a specific date
                val setRef = db.collection(userUid)
                    .document(date)
                    .collection(date)
                    .document(exName)
                    .collection(exName).get().await()

                //for each on the documents within setRef subcollection
                setRef?.documents?.forEach { set ->
                    //Create objects based off of fields in documents
                    if (set != null) {
                        if (exType == "STATIC") {
                            Log.d(TAG, "ADDING STATICs")
                            setBundle.add(
                                Statics(
                                    name = set.get("name") as String,
                                    holdTime = set.get("holdTime") as String,
                                    weight = set.get("weight") as String,
                                    sir = set.get("sir") as String,
                                    progression = set.get("progression") as String,
                                    setNumber = set.get("setNumber") as Long,
                                    iconId = -1,
                                    properties = set.get("properties") as MutableMap<String, Boolean>
                                )
                            )
                        }

                        if (exType == "DYNAMIC") {
                            setBundle.add(
                                Dynamics(
                                    name = set.get("name") as String,
                                    weight = set.get("weight") as String,
                                    reps = set.get("reps") as String,
                                    rir = set.get("rir") as String,
                                    setNumber = set.get("setNumber") as Long,
                                    iconId = -1,
                                    properties = set.get("properties") as MutableMap<String, Boolean>)
                            )
                        }
                    }
                }

                if(setBundle.size > 0){
                    exerciseBundle.add(setBundle)

                    //setBundle.clear()
                }
            }
            else {
                return Resource.Error("No subcollection for this date")
            }
        }
        //Log.d(TAG, "${exerciseBundle[0][0].name}")
        return Resource.Success(exerciseBundle)
    }

    override suspend fun addSet(movement: AllExercises): Resource<String> {
        TODO("Not yet implemented")
    }

}