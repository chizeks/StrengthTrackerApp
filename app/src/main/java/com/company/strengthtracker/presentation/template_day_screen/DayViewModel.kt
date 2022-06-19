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
import com.company.strengthtracker.data.entities.exercise_data.main_categories.*
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
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DayViewModel @Inject constructor(
    private val setRepositoryImpl: SetRepositoryImpl,
) : ViewModel() {
    // registerScreenState.value = RegisterScreenState.LOADING


    //db reference for queries
    val db = Firebase.firestore

    private val _exerciseTypes = mutableStateOf(TypeDictionary().typeDictionary)
    val exerciseTypes = _exerciseTypes

    //state enum for UI control
    enum class DayScreenState {
        LAUNCH, LOADING, LOADED, SELECT, SELECTED
    }



    //screenstate
    private val _dayScreenState = mutableStateOf(DayScreenState.LAUNCH)

    //visible screenstate
    val dayScreenState = _dayScreenState

    //set list for selected date
    private val _exerciseBundleMain = mutableStateListOf<MutableList<AllExercises>>()

    //visible set list
    val exerciseBundleMain = _exerciseBundleMain

    private val _exList = mutableStateListOf<AllExercises>()
    val exList = _exList

    //date-default: current date
    private var _dateIn = mutableStateOf(LocalDate.now())
    private val formatter:DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val dateIn = _dateIn
    var date = mutableStateOf( formatter.format(dateIn.value))

    fun updateDate(newValue: LocalDate) {
        _dateIn.value = newValue
        date = mutableStateOf(formatter.format(dateIn.value))
        exerciseBundleMain.clear()
        getSetDataForDate()
        Log.d(TAG, "Date is now: ${_dateIn.value}")
        Log.d(TAG, "Date is now: ${date.value}")
    }

    fun openSelection() {
        dayScreenState.value = DayScreenState.SELECT
    }

    fun closeSelection() {
        if(exerciseBundleMain.size > 0)
             dayScreenState.value = DayScreenState.LOADED
    }

    init {
        getSetDataForDate()
    }

    suspend fun ultimateBruhHelper() {
        val docRef =
            db.collection("test").document(
                dateIn.value.toString()
            ).collection(dateIn.value.toString()).get()
                .addOnSuccessListener { exercises ->
                    for (exercise in exercises) {
                        val docRef2 = db.collection("test")
                            .document(dateIn.value.toString())
                            .collection(dateIn.value.toString())
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
                                        )
                                    )
                                }
                                if(exerciseBundleMain.size == 1 && exerciseBundleMain.get(0).get(0).name == setBundle.get(0).name){
                                    exerciseBundleMain.set(0, setBundle)
                                }
                                else {
                                    exerciseBundleMain.add(setBundle)
                                }

                            }

                    }
                }.addOnFailureListener {

                }
    }


    fun getSetDataForDate() {
        viewModelScope.launch { ->

            dayScreenState.value = DayScreenState.LOADING

            ultimateBruhHelper()
            dayScreenState.value = DayScreenState.LOADED
        }

    }

    fun addStaticsSet(
        movement: AllExercises
    ) {
        val docData = hashMapOf(
            "name" to movement.name
        )
        val docref = db.collection("test").document(dateIn.value.toString())
            .collection(dateIn.value.toString()).document(movement.name).set(docData)

        db.collection("test").document(dateIn.value.toString()).collection(dateIn.value.toString())
            .document(movement.name, ).collection(movement.name).add(movement)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: ${documentReference.id} and curdate is ${date}"
                )
                getSetDataForDate()
            }
    }

    fun filterTypeList(){
        val fBundle = _exerciseBundleMain
        var tempTypeList = _exerciseTypes.value

        val indexList:MutableList<AllExercises> = mutableListOf()
        Log.d(TAG,"Bruh---->" + tempTypeList.size.toString())
        if(fBundle.size > 0){
            for(i in 0..fBundle.size - 1){
                var tempType = fBundle.get(i).get(0)
                Log.d(TAG, "Check tempType" + tempType.name)
                for(j in 0..tempTypeList.size - 1){
                    Log.d(TAG, "check--->" + tempTypeList.get(j).name)
                    if(tempType.name == tempTypeList.get(j).name){
                       //tempTypeList.removeAt(j)
                        indexList.add(tempTypeList.get(j))
                    }
                }
            }
            indexList.forEach { it ->
                tempTypeList.remove(it)
            }
        }

    }

}

