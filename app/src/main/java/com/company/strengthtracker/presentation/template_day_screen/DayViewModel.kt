package com.company.strengthtracker.presentation.template_day_screen

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.strengthtracker.data.entities.exercise_data.ExerciseSet
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Planche
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.TypeDictionary
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.company.strengthtracker.presentation.register_screen.RegisterViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DayViewModel @Inject constructor(
    private val setRepositoryImpl: SetRepositoryImpl,

    ) : ViewModel() {
    // registerScreenState.value = RegisterScreenState.LOADING


    //db reference for queries
    val db = Firebase.firestore

    //state enum for UI control
    enum class DayScreenState {
        LAUNCH, LOADING, LOADED, NODATA, SELECT, SELECTED
    }


    //screenstate
    private val _dayScreenState = mutableStateOf(DayScreenState.LAUNCH)
    //visible screenstate
    val dayScreenState = _dayScreenState

    //set list for selected date
    private val _exerciseBundleMain = mutableStateListOf<MutableList<AllExercises>>()
    //visible set list
    val exerciseBundleMain = _exerciseBundleMain

    //date-default: current date
    private var _dateIn = mutableStateOf(LocalDate.now())
    var dateIn = _dateIn
    val date = dateIn.toString()

    fun updateDate(newValue: LocalDate) {
        dateIn.value = newValue
        Log.d(TAG, "Date is now: ${date}")
    }


    suspend fun ultimateBruhHelper() {
        val docRef =
            db.collection("test").document(
                    "2022-06-12").collection("2022-06-12").get()
                .addOnSuccessListener { exercises ->
                    for (exercise in exercises) {
                        val docRef2 = db.collection("test")
                            .document("2022-06-12")
                            .collection("2022-06-12")
                            .document(exercise.get("name").toString())
                            .collection(exercise.get("name").toString()).get()
                            .addOnSuccessListener { sets ->
                                var setBundle: MutableList<AllExercises> by mutableStateOf(
                                    mutableListOf()
                                )
                                for (set in sets) {
                                    Log.d(TAG, "${set.data}")
                                    setBundle.add(
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
                                }
                                exerciseBundleMain.add(setBundle)
                            }

                    }
                }.addOnFailureListener{

                }
    }

    fun getSetDataForDate() {
        viewModelScope.launch { ->
            dayScreenState.value = DayScreenState.LOADING
            ultimateBruhHelper()
            if(exerciseBundleMain.size < 1){
                dayScreenState.value = DayScreenState.LOADED
            }
            else if (exerciseBundleMain.size > 0) {
                dayScreenState.value = DayScreenState.NODATA
            }
        }
    }

    fun addStaticsSet(
        movement: AllExercises
    ) {

        db.collection("test").document(date.toString()).collection(date.toString())
            .document(movement.name).collection(movement.name).add(movement)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: ${documentReference.id} and curdate is ${date}"
                )
            }
    }

}

