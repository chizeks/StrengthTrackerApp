package com.company.strengthtracker.presentation.template_day_screen


import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.CalendarViewWeek
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.company.strengthtracker.presentation.template_day_screen.DayViewModel.DayScreenState.*
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel(),
) {
    val typeList = remember { viewModel.exerciseTypes }
    val exList = remember { viewModel.exList }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val colors = MaterialTheme.colorScheme

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
            .background(color = colors.background),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) { //fades in/out child element
        TopBar(viewModel = viewModel, date = date, colors)
        when (screenState) {
            LAUNCH -> {
                //TopBar(viewModel = viewModel, date = date, colors)

            }
            LOADING -> {


            }
            ERROR -> {
                Text("Error")
                scope.launch {
                    snackbarHostState.showSnackbar("There was an error retrieving log data for this day")
                }
            }
            EMPTY -> {
                //TopBar(viewModel = viewModel, date = date, colors)
                Column(
                    modifier = Modifier.fillMaxHeight(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "Empty day...",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )

                }
                BottomBar(viewModel = viewModel, date = date, colors = colors)
            }
            LOADED -> {
                // TopBar(viewModel = viewModel, date = date, colors)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    exerciseBundle.forEachIndexed { index, element ->

                        ExpandableExerciseCard(
                            movement = element.get(0),
                            date = date.value,
                            exercises = exerciseBundle.get(index)
                        )

                    }


                }
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    BottomBar(viewModel = viewModel, date = date, colors = colors)
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
fun BottomBar(
    viewModel: DayViewModel,
    date: MutableState<LocalDate>,
    colors: ColorScheme,
) {

    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FloatingActionButton(
            onClick = { viewModel.openSelection() },
            containerColor = colors.primaryContainer,
            contentColor = colors.onPrimaryContainer
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "selectionview",
                tint = colors.primary
            )
        }
    }

}


@Composable
fun TopBar(
    viewModel: DayViewModel,
    date: MutableState<LocalDate>,
    colors: ColorScheme
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
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //opens calendar
        IconButton(
            modifier = Modifier
                .alpha(.8f),
            onClick = {
                exState = !exState
            },

            ) {
            Icon(
                imageVector = Icons.Sharp.CalendarViewWeek,
                contentDescription = "switch to month view",
                tint = colors.primary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(formatter.format(date.value), color = colors.primary, modifier = Modifier)
        }
    }
}





