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
import com.company.strengthtracker.di.AppModule
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
import java.lang.Exception
import java.nio.file.Path
import java.time.LocalDate
import javax.inject.Inject
//
class SetRepositoryImpl @Inject constructor(
    private val db:FirebaseFirestore
) : SetRepository {

    override suspend fun setUpdaterNewVersion(
        date: String,
        userUid: String
    ): Resource<QuerySnapshot> {
        //reference to collection of exercises logged by the user on a specified date
        val allExercisesRef = db.collection(userUid)
            .document(date)
            .collection(date).get().await()
        return if(allExercisesRef.documents.size > 0)
            Resource.Success(allExercisesRef)
        else if (allExercisesRef.documents.size == 0){
            Resource.Error("empty-day")
        }
        else
            Resource.Error("Error retrieving collection for this date")
    }

    override suspend fun getSetSubcollection(
        date: String,
        userUid: String,
        exName: String
    ): Resource<QuerySnapshot> {
        val setsRef = db.collection(userUid).document(date).collection(date).document(exName).collection(exName).get().await()
        return if(setsRef.documents.size > 0)
            Resource.Success(setsRef)
        else Resource.Error("Error retrieving sets for this exercise")
    }

    override suspend fun addSet(
        movement: AllExercises,
        date:String,
        userUid:String
    ):Resource<String> {
//        val docData = hashMapOf(
//            "name" to movement.name,
//            "exType" to movement.exType
//        )
//
//        //adds fields to the subcollection for each individual exercise for further organization
//        val pathFieldCreator = db.collection(userUid).document(date)
//            .collection(date).document(movement.name).set(docData)
//
//        val newDoc = db.collection(userUid).document(date)
//            .collection(date)
//            .document(movement.name).collection(movement.name).add(movement).addOnCompleteListener {task ->
//                if(task.isSuccessful){
//                    return@addOnCompleteListener Resource.Success(task.result.id.toString())
//                }
//
//            }
        return Resource.Success("bruh")
    }


}