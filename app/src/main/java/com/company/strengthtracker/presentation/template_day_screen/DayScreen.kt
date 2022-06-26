package com.company.strengthtracker.presentation.template_day_screen

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.CalendarViewWeek
import androidx.compose.material.icons.sharp.Notes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.*
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.TypeDictionary
import com.company.strengthtracker.ui.theme.*
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.theme.KalendarShape
import java.time.LocalDate
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel(),
) {
    val typeList = remember { viewModel.exerciseTypes }
    val exList = remember { viewModel.exList }


    //State reference
    val screenState by remember { viewModel.dayScreenState }
    //holds date on select from calendar and on open app.
    //var dateString = remember { viewModel.date.value.toString() }
    var date = remember { viewModel.dateIn }
    //holder for passing data back to UI from viewmodel
    var exerciseBundle = remember { viewModel.exerciseBundleMain }

    //Controls Calendar state
    var exState by remember { mutableStateOf(false) }
//    MaterialTheme(
//        colorScheme = MaterialTheme.colorScheme,
//        shapes = MaterialTheme.shapes,
//        typography = MaterialTheme.typography
//    ) {


    //holder for main screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            )
            .background(color = MaterialTheme.colorScheme.background),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) { //fades in/out child element
        when (screenState) {
            DayViewModel.DayScreenState.LAUNCH -> {
                TopAppBar(viewModel = viewModel, date = date)

            }
            DayViewModel.DayScreenState.LOADING -> {
                Text(text = "Loading")
            }
            DayViewModel.DayScreenState.LOADED -> {
                Log.d(TAG, "ExBundle SIZE ---> " + exerciseBundle.size.toString())

                TopAppBar(viewModel = viewModel, date = date)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                        exerciseBundle.forEachIndexed { index, element ->
                            if(element[0] != null) {
                                ExpandableExerciseCard(
                                    movement = element.get(0),
                                    date = date.value,
                                    exercises = exerciseBundle.get(index)
                                )
                            }
                        }


                }
            }
            DayViewModel.DayScreenState.SELECT -> {
                //viewModel.filterTypeList()
                SelectionColumn(exerciseList = typeList.value, viewModel = viewModel)
                //Divider(modifier = Modifier.fillMaxWidth(1f))
            }

        }
    }
    //}
}

@Composable
fun TopAppBar(
    viewModel: DayViewModel,
    date: MutableState<LocalDate>
) {
    var exState by remember { mutableStateOf(false) }
    val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    AnimatedVisibility(visible = exState, enter = fadeIn(), exit = fadeOut()) {

        ExpandCalendar(updateDay = {
            //date lambda
            viewModel.updateDate(it) //updating vm
        })
    }


    //Holder for top bar buttons and stuff
    Row(
        modifier = Modifier
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //opens calendar
        IconButton(
            modifier = Modifier
                .alpha(.8f)
                .weight(1f),
            onClick = {
                exState = !exState
            },

            ) {
            Icon(
                imageVector = Icons.Sharp.CalendarViewWeek,
                contentDescription = "switch to month view",
                tint = MaterialTheme.colorScheme.primary
            )
        }
//        OutlinedTextField(
//            value = dateString,
//            modifier = Modifier.width(200.dp),
//            onValueChange = {
//
//            },
//
//
//            textStyle = TextStyle(textAlign = TextAlign.Center,
//                fontSize = 20.sp),
//        )
        Text(formatter.format(date.value), color = MaterialTheme.colorScheme.primary)
        //does nothing currently
        IconButton(
            modifier = Modifier
                .alpha(.8f)
                .weight(1f),
            onClick = {
                viewModel.openSelection()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "selectionview",
                tint = MaterialTheme.colorScheme.primary
            )

        }
    }

}





