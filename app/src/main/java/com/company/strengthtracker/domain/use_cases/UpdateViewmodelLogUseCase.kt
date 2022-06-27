package com.company.strengthtracker.domain.use_cases

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.data.repository.UsersRepositoryImpl
import com.company.strengthtracker.domain.util.Resource
import com.company.strengthtracker.presentation.template_day_screen.DayViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateViewmodelLogUseCase @Inject constructor(
    private val setRepositoryImpl: SetRepositoryImpl
) {
    val TAG = "useCase_log --->"
    val errorException: Exception = Exception()
    suspend fun updateViewLogUseCase(
        date: String,
        userUid: String
    ): Resource<MutableList<MutableList<AllExercises>>> {
        val exerciseBundleTemp: MutableList<MutableList<AllExercises>> = mutableListOf()

        val response = setRepositoryImpl.setUpdater(
            date = date,
            userUid = userUid,
            exerciseBundle = exerciseBundleTemp
        )

        when {
            response is Resource.Error -> {
                return Resource.Error("resource-error")
            }
            response is Resource.Success && response.data?.size!! > 0 -> {

                return response
            }
            response is Resource.Success && response.data?.size!! <= 0 -> {

                return Resource.Error(message = "empty-day")
            }
            else -> {
                return Resource.Error("unknown-error")
            }
        }
    }
}
//
//
//    fun getSetDataForDate() {
//        viewModelScope.launch { ->
//            val fetchData: Resource<MutableList<MutableList<AllExercises>>>
//            val exerciseBundleTemp:MutableList<MutableList<AllExercises>> = mutableListOf()
//
//            //get logged in users UID
//            val response = authRepositoryImpl.getCurrentUser()?.uid
//            if(response != null){
//                //Call set updater to refresh list of logged exercises
//                fetchData = usersRepositoryImpl.setUpdater(date = dateIn.value.toString(), userUid = response, exerciseBundle = exerciseBundleTemp)
//
//                //Success
//                if(fetchData is Resource.Success && fetchData.data.size > 0){
//                    Log.d(ContentValues.TAG, "Size of bundle in VM ${exerciseBundleMain.size}")
//                    exerciseBundleMain.addAll(fetchData.data)
//                    Log.d(ContentValues.TAG, "Size of bundle ${exerciseBundleMain.size}")
//                }
//
//                dayScreenState.value = when (fetchData) {
//                    is Resource.Success-> DayViewModel.DayScreenState.LOADED
//                    is Resource.Error -> DayViewModel.DayScreenState.LAUNCH
//                }
//            }
////            _dayScreenState.value = DayScreenState.LOADED
//        }
//
//    }
