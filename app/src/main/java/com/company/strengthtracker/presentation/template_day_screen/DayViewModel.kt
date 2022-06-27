package com.company.strengthtracker.presentation.template_day_screen

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.User
import com.company.strengthtracker.data.entities.exercise_data.main_categories.*
import com.company.strengthtracker.data.repository.AuthRepositoryImpl
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import com.company.strengthtracker.domain.use_cases.UpdateViewmodelLogUseCase
import com.company.strengthtracker.domain.util.Resource
import com.company.strengthtracker.presentation.register_screen.RegisterViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val setRepositoryImpl: SetRepositoryImpl,
    private val updateViewModel: UpdateViewmodelLogUseCase,
) : ViewModel() {
    // registerScreenState.value = RegisterScreenState.LOADING


    //db reference for queries
    val db = Firebase.firestore

    private val _exerciseTypes = mutableStateOf(TypeDictionary().typeDictionary)
    val exerciseTypes = _exerciseTypes

    //state enum for UI control
    enum class DayScreenState {
        LAUNCH, LOADING, LOADED, SELECT, EMPTY, ERROR
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
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val dateIn = _dateIn
    var date = mutableStateOf(formatter.format(dateIn.value))

    fun updateDate(newValue: LocalDate) {
        dayScreenState.value = DayScreenState.LOADING
        _dateIn.value = newValue
        exerciseBundleMain.clear()
        getSetDataForDate()
    }

    fun openSelection() { _dayScreenState.value = DayScreenState.SELECT }

    fun closeSelection() { _dayScreenState.value = DayScreenState.LOADED }

    init {

            getSetDataForDate()
    }


    fun getSetDataForDate() {
        viewModelScope.launch { ->
            //get logged in users UID
            val response = authRepositoryImpl.getCurrentUser()?.uid
            if(response != null){
                //Call set updater to refresh list of logged exercises
                val updateLog = updateViewModel.updateViewLogUseCase(date = dateIn.value.toString(), userUid = response)
                //Success
                Log.d(TAG, "${updateLog.message} WHAT THE FUCK BRO")
                when(updateLog){
                    is Resource.Success -> {
                        exerciseBundleMain.clear()
                        exerciseBundleMain.addAll(updateLog.data)
                        Log.d(TAG, exerciseBundleMain[0][0].properties.toString())
                        dayScreenState.value = DayScreenState.LOADED
                    }
                    is Resource.Error -> {
                        if(updateLog.message == "empty-day"){
                            Log.d(TAG, "${updateLog.message} HELLO")
                            dayScreenState.value = DayScreenState.EMPTY
                        }
                        else{
                            Log.d("BRUH", "${updateLog.message} WHuh")
                            dayScreenState.value = DayScreenState.ERROR
                        }
                    }
                }
            }
        }
    }

    fun addNewSet(movement: AllExercises){
        viewModelScope.launch {
           addSet(movement = movement)
        }
    }


    suspend fun addSet(
        movement: AllExercises
    ) = try {
        val docData = hashMapOf(
            "name" to movement.name,
            "exType" to movement.exType
        )

        val response = authRepositoryImpl.getCurrentUser()?.uid

        if (response != null) {
            val docref = db.collection(response).document(dateIn.value.toString())
                .collection(dateIn.value.toString()).document(movement.name).set(docData)

            db.collection(response).document(dateIn.value.toString())
                .collection(dateIn.value.toString())
                .document(movement.name).collection(movement.name).add(movement)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: ${documentReference.id} and curdate is ${date}"
                    )
                    getSetDataForDate()
                }
            Resource.Success("Set logged")
        } else Resource.Error("Error has occurred logging set")
    } catch (e: Exception) {
        Resource.Error("${e.message}")
    }



    fun filterTypeList() {
        val fBundle = _exerciseBundleMain
        var tempTypeList = _exerciseTypes.value

        val indexList: MutableList<AllExercises> = mutableListOf()
        Log.d(TAG, "Bruh---->" + tempTypeList.size.toString())
        if (fBundle.size > 0) {
            for (i in 0..fBundle.size - 1) {
                var tempType = fBundle.get(i).get(0)
                Log.d(TAG, "Check tempType" + tempType.name)
                for (j in 0..tempTypeList.size - 1) {
                    Log.d(TAG, "check--->" + tempTypeList.get(j).name)
                    if (tempType.name == tempTypeList.get(j).name) {
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

