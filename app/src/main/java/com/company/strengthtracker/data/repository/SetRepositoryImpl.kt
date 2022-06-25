package com.company.strengthtracker.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
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
import java.time.LocalDate
import javax.inject.Inject
//
//class SetRepositoryImpl @Inject constructor(
//    private val db:FirebaseFirestore = Firebase.firestore
//) : SetRepository {




//    override suspend fun updateDay(dateIn: MutableState<LocalDate>, exerciseBundleMain:MutableList<MutableList<AllExercises>>) = try {
//        val docRef =
//            db.collection("test").document(
//                dateIn.value.toString()
//            ).collection(dateIn.value.toString()).get()
//                .addOnSuccessListener { exercises ->
//                    for (exercise in exercises) {
//                        val docRef2 = db.collection("test")
//                            .document(dateIn.value.toString())
//                            .collection(dateIn.value.toString())
//                            .document(exercise.get("name").toString())
//                            .collection(exercise.get("name").toString()).get()
//                            .addOnSuccessListener { sets ->
//                                var setBundle: MutableList<AllExercises> by mutableStateOf(
//                                    mutableListOf()
//                                )
//                                for (set in sets) {
//                                    Log.d(ContentValues.TAG, "${set.data}")
//                                    setBundle.add(
//                                        Statics(
//                                            name = set.get("name") as String,
//                                            holdTime = set.get("holdTime") as String,
//                                            weight = set.get("weight") as String,
//                                            sir = set.get("sir") as String,
//                                            progression = set.get("progression") as String,
//                                            setNumber = set.get("setNumber") as Long,
//                                        )
//                                    )
//                                }
//                                if (exerciseBundleMain.size == 1 && exerciseBundleMain.get(0)
//                                        .get(0).name == setBundle.get(0).name
//                                ) {
//                                    exerciseBundleMain.set(0, setBundle)
//                                } else {
//                                    exerciseBundleMain.add(setBundle)
//                                }
//
//                            }
//
//                    }
//                }.addOnFailureListener {
//
//                }
//    }
//}