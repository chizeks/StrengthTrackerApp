package com.company.strengthtracker.presentation.template_day_screen

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
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
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
    private val usersRepositoryImpl: UsersRepositoryImpl
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
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val dateIn = _dateIn
    var date = mutableStateOf(formatter.format(dateIn.value))

    fun updateDate(newValue: LocalDate) {
        _dateIn.value = newValue
        exerciseBundleMain.clear()
        getSetDataForDate()
    }

    fun openSelection() { _dayScreenState.value = DayScreenState.SELECT }

    fun closeSelection() { _dayScreenState.value = DayScreenState.LOADED }

    init {

       getSetDataForDate()
    }

//    override suspend fun getUserByUid(uid: String): Resource<User?> {
//        return try {
//            val response = usersCollection.document(uid).get().await()
//            if (response != null) {
//                val user = response.toObject(User::class.java)
//                return Resource.Success(data = user)
//            } else Resource.Error("Error in login")
//
//        } catch (e: Exception) {
//            Resource.Error("${e.message}")
//        }
//    }

    /*Collection path: uid/date/date/exerciseName/exerciseName/setDoc*/
//    suspend fun updateSetData() = try {
//        val response = authRepositoryImpl.getCurrentUser()?.uid //Retrieving uid of current user
//        if (response != null) {
//            Log.d(TAG, response)
//            //Creates or finds subcollection under exercise name
//            db.collection(response)
//                .document(dateIn.value.toString())
//                .collection(dateIn.value.toString())
//                .get()
//                .addOnSuccessListener { exercises ->
//                    //For each subcollection, all sets, in all logged exercise subcollections are collected
//                    for (exercise in exercises) {
//                        //getting specific set data
//
//                         db.collection(response)
//                            .document(dateIn.value.toString())
//                            .collection(dateIn.value.toString())
//                            .document(exercise.get("name").toString())
//                            .collection(exercise.get("name").toString()).get()
//                            .addOnSuccessListener { sets ->
//                                var setBundle: MutableList<AllExercises> by mutableStateOf(
//                                    mutableListOf()
//                                )
//                                //Adding setData to list for UI
//                                for (set in sets) {
//                                    Log.d(TAG, "${set.data}")
//                                    if(exercise.get("exType").toString() == "STATIC"){}
//                                        setBundle.add(
//                                            Statics(
//                                                name = set.get("name") as String,
//                                                holdTime = set.get("holdTime") as String,
//                                                weight = set.get("weight") as String,
//                                                sir = set.get("sir") as String,
//                                                progression = set.get("progression") as String,
//                                                setNumber = set.get("setNumber") as Long,
//                                            )
//                                        )
//                                    if (set.get("exType").toString() == "DYNAMIC"){
//                                        setBundle.add(
//                                            Dynamics(
//
//                                            )
//                                        )
//                                    }
//                                }
//                                if (exerciseBundleMain.size == 1 && exerciseBundleMain.get(0)
//                                        .get(0).name == setBundle.get(0).name
//                                ) {
//                                    exerciseBundleMain.set(0, setBundle)
//                                } else {
//                                    exerciseBundleMain.add(setBundle)
//                                }
//
//                            }.addOnFailureListener {
//                                throw Exception("Data fetch failed on get documents in subcol")
//                            }
//
//                    }
//                }.addOnFailureListener {
//                    throw Exception("Data fetch failed on get exercise subcol")
//                }
//            Resource.Success("Data updated successfully")
//        } else {
//            Resource.Error("Error fetching uid")
//        }
//    } catch (e: Exception) {
//        Resource.Error("Error updating data")
//    }


    fun getSetDataForDate() {
        viewModelScope.launch { ->
            val fetchData:Resource<MutableList<MutableList<AllExercises>>>
            val exerciseBundleTemp:MutableList<MutableList<AllExercises>> = mutableListOf()

            //get logged in users UID
            val response = authRepositoryImpl.getCurrentUser()?.uid
            if(response != null){
                //Call set updater to refresh list of logged exercises
                fetchData = usersRepositoryImpl.setUpdater(date = dateIn.value.toString(), userUid = response, exerciseBundle = exerciseBundleTemp)

                //Success
                if(fetchData is Resource.Success && fetchData.data.size > 0){
                    Log.d(TAG, "Size of bundle in VM ${exerciseBundleMain.size}")
                    exerciseBundleMain.addAll(fetchData.data)
                    Log.d(TAG, "Size of bundle ${exerciseBundleMain.size}")
                }

                dayScreenState.value = when (fetchData) {
                    is Resource.Success-> DayViewModel.DayScreenState.LOADED
                    is Resource.Error -> DayViewModel.DayScreenState.LAUNCH
                }
            }
//            _dayScreenState.value = DayScreenState.LOADED
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
                        TAG,
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

