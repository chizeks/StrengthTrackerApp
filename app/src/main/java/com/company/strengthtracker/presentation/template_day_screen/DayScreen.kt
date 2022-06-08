package com.company.strengthtracker.presentation.template_day_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.CalendarViewWeek
import androidx.compose.material.icons.sharp.Notes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.*
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.ui.theme.*
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.theme.KalendarShape

import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel()

) {

    val list = remember {
        mutableStateListOf<AllExercises>()
    }


    var expandedPicker by remember { mutableStateOf(false) }
    var exState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AnimatedVisibility(visible = exState, enter = fadeIn(), exit = fadeOut()) {
            ExpandCalendar(updateDay = { date = it })
        }

        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(2f)
            ) {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {
                        exState = !exState
                    }
                ) {

                    Icon(
                        imageVector = Icons.Sharp.CalendarViewWeek,
                        contentDescription = "switch to month view"
                    )

                }
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Sharp.Notes,
                        contentDescription = "note-view"
                    )

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.305f)
            ) {
                OutlinedTextField(
                    value = date.toString(),
                    onValueChange = {/*Check for day data and load */
                    },
                    enabled = false,
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            ExpandableExerciseCard(movement = Planche(), date = date)

        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownCustomItem(
    content: String,

    ) {
    Card(
        modifier = Modifier.fillMaxWidth(0.95f),
        onClick = {}
    ) {
        Text(text = content)
    }
}

@ExperimentalMaterialApi
@Composable
fun ExpandableExerciseCard(
    movement: AllExercises,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    width: Float = 1.0f,
    padding: Dp = 10.dp,
    date: LocalDate
) {
    val title = movement.name
    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue =
        if (expandedState) 180f
        else 0f
    )


    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            )
            .padding(padding)
            .shadow(3.dp),
        shape = Shapes.medium,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth(),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
                Row() {
                    Column() {

                    }
                }
            }

            if (expandedState) {

                if (movement is Statics) {

                    StaticsAddSetPopUp(movement = movement, date = date)
                    //StaticsTextFields(movement = movement)
                } else if (movement is Dynamics) {
                    //  DynamicsTextFields(movement = movement)
                }
            }
        }
    }
}

@Composable
fun StaticsAddSetPopUp(
    movement: Statics,
    date: LocalDate,
    viewModel: DayViewModel = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(true) }
    var textContentProgression by remember { mutableStateOf("") }
    var textContentAssistance by remember { mutableStateOf("") }
    var textContentTime by remember { mutableStateOf("") }
    var textContentSiR by remember { mutableStateOf("") }
    var test by remember { mutableStateOf("Add") }
    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties()
        ) {
            Card(
                modifier = Modifier
                    .size(300.dp, 370.dp)
                    .background(Color.White),
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DropDownTextField(
                        label = "Progression",
                        movement = movement
                    ) { textContentProgression = it }
                    TimeIncrementTextField(
                        label = "Time",
                        movement = movement,
                        trailingLabel = "sec",
                        onDataChanged = {
                            textContentTime = it
                        }
                    )
                    WeightIncrementTextField(
                        movement = movement,
                        label = "Assistance",
                        trailingLabel = "kg."
                    ) { textContentAssistance = it }
                    TimeIncrementTextField(
                        movement = movement,
                        label = "SiR",
                        trailingLabel = "sec"
                    ) { textContentSiR = it }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        //add set
                        Button(
                            onClick = {
                                /*PASS DATA TO VIEWMODEL, IMMEDIATELY MAKE NEW DOCUMENT*/
                                viewModel.addStaticsSet(
                                    name = movement.name,
                                    progression = textContentProgression,
                                    time = textContentTime,
                                    assistance = textContentAssistance,
                                    SiR = textContentSiR,
                                    date = date
                                )
                                openDialog = false

                            },
                            elevation = ButtonDefaults.elevation(0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        ) {
                            Text(text = textContentTime)
                        }
                        //cancel add
                        Button(
                            onClick = { openDialog = false },
                            elevation = ButtonDefaults.elevation(0.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        ) {
                            Text(text = "Cancel")
                        }
                    }


                }


            }
        }
    }

}

@Composable
fun WeightIncrementTextField(
    movement: Statics,
    label: String,
    trailingLabel: String,
    onDataChanged: (String) -> Unit
) {
    var content by remember { mutableStateOf("0") }
    var trailingIconText by remember { mutableStateOf(trailingLabel) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .scale(scaleY = 0.9f, scaleX = 1f),
            value = content,
            label = {
                Text(text = label)
            },
            onValueChange = {
                content = it
                onDataChanged(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = brightGrey,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = {
                        if (trailingIconText.equals("kg."))
                            trailingIconText = "lb."
                        else
                            trailingIconText = "kg."
                    }) {
                    Text(text = trailingIconText)
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .padding(top = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 5.dp)
            )
            {
                IconButton(
                    onClick = {
                        if (content.isDigitsOnly()) {
                            content = (content.toInt() + 1).toString()
                            onDataChanged(content)
                        } else
                            content = 0.toString()
                    },
                    modifier = Modifier.size(19.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "increment-seconds")
                }
                IconButton(onClick = {
                    if (content.toInt() > 0) {
                        content = (content.toInt() - 1).toString()
                        onDataChanged(content)
                    } else if (!content.isDigitsOnly()) {
                        content = 0.toString()
                    }

                }, modifier = Modifier.size(21.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Minimize,
                        contentDescription = "decrement-seconds",
                       // modifier = Modifier.scale(scaleX = 1.05f, scaleY = 1.05f),

                    )
                }
            }
        }

    }
}

@Composable
fun TimeIncrementTextField(
    movement: Statics,
    label: String,
    trailingLabel: String,
    onDataChanged: (String) -> Unit
) {
    var content by remember { mutableStateOf("0") }
    var trailingIconText by remember { mutableStateOf("sec.") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .scale(scaleY = 0.9f, scaleX = 1f),
            value = content,
            label = {
                Text(text = label)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                content = it
                onDataChanged(it)

                // movement.holdTime = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = brightGrey,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Button(
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = {

                    }) {
                    Text(text = trailingIconText)
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .padding(top = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 5.dp)
            )
            {
                IconButton(
                    onClick = {
                        content = (content.toInt() + 1).toString()
                        onDataChanged(content)
                    },
                    modifier = Modifier.size(19.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "increment-seconds")
                }
                IconButton(onClick = {
                    content = (content.toInt() - 1).toString()
                    onDataChanged(content)
                }, modifier = Modifier.size(21.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Minimize,
                        contentDescription = "decrement-seconds",
                        modifier = Modifier.scale(scaleX = 1.05f, scaleY = 1.05f)
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownTextField(
    movement: Statics,
    label: String,
    onDataChanged: (String) -> Unit
) {
    var prog by remember { mutableStateOf("") }
    var dropdown by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            value = prog,
            label = { Text(text = label) },
            leadingIcon = {
                IconButton(
                    onClick = { dropdown = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "drop-down-arrow"
                    )
                }
            },
            onValueChange = { prog = it },
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = brightGrey,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Black,
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.scale(scaleY = 0.9f, scaleX = 1f),
        )
        DropdownMenu(expanded = dropdown, onDismissRequest = { dropdown = false }) {
            var progList = Progressions().progressions
            progList.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    prog = progList.get(index)
                    onDataChanged(prog)
                    dropdown = false
                }) {
                    Text(text = progList.get(index))
                }
            }
        }
    }
}

@Composable
fun customCalendar(
    state: Boolean,
    updateDay: (LocalDate) -> Unit
) {
    if (state) {
        MaterialTheme(
            colors = Colors(
                primary = Color.White,
                primaryVariant = davysGrey,
                secondary = davysGrey,
                secondaryVariant = davysGrey,
                background = davysGrey,
                surface = davysGrey,
                error = Color.Red,
                onPrimary = brightGrey,
                onSecondary = davysGrey,
                onBackground = davysGrey,
                onSurface = davysGrey,
                onError = davysGrey,
                isLight = true
            )
        ) {
            Kalendar(
                kalendarType = KalendarType.Oceanic(),
                kalendarStyle = KalendarStyle(
                    kalendarBackgroundColor = Color.White,
                    kalendarColor = Color.White,
                    elevation = 0.dp,
                    //kalendarSelector = KalendarSelector.CutCornerSquare(),
                    shape = KalendarShape.ButtomCurvedShape,
                    kalendarSelector = KalendarSelector.Dot(
                        selectedColor = MaterialTheme.colors.primaryVariant,
                        todayColor = spanishGrey,
                        selectedTextColor = Color.Black,
                        defaultTextColor = Color.Black,
                        eventTextColor = Color.White,
                        defaultColor = Color.White
                    )
                ),

                onCurrentDayClick = { day, event ->
                    updateDay(day)
                },
                errorMessage = {
                    //Handle the error if any
                })

        }
    } else {
        MaterialTheme(
            colors = Colors(
                primary = Color.White,
                primaryVariant = davysGrey,
                secondary = davysGrey,
                secondaryVariant = davysGrey,
                background = davysGrey,
                surface = davysGrey,
                error = Color.Red,
                onPrimary = brightGrey,
                onSecondary = davysGrey,
                onBackground = davysGrey,
                onSurface = davysGrey,
                onError = davysGrey,
                isLight = true
            )
        ) {
            Kalendar(
                kalendarType = KalendarType.Firey(),
                kalendarStyle = KalendarStyle(
                    kalendarBackgroundColor = Color.White,
                    kalendarColor = Color.White,
                    elevation = 0.dp,
                    //kalendarSelector = KalendarSelector.CutCornerSquare(),
                    shape = KalendarShape.ButtomCurvedShape,
                    kalendarSelector = KalendarSelector.Dot(
                        selectedColor = MaterialTheme.colors.primaryVariant,
                        todayColor = spanishGrey,
                        selectedTextColor = Color.Black,
                        defaultTextColor = Color.Black,
                        eventTextColor = Color.White,
                        defaultColor = Color.White
                    )
                ),

                onCurrentDayClick = { day, event ->
                    updateDay(day)
                    //handle the date click listener
                },
                errorMessage = {
                    //Handle the error if any
                })

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun ExpandCalendar(
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    width: Float = 1.0f,
    padding: Dp = 10.dp,
    updateDay: (LocalDate) -> Unit
) {
    var expandedState by remember { mutableStateOf(true) }
    var curDate by remember { mutableStateOf(LocalDate.now()) }

    val rotationState by animateFloatAsState(
        targetValue =
        if (expandedState) 180f
        else 0f
    )


    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(padding)
            .shadow(10.dp),
        shape = Shapes.medium,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {

            var animationState by remember { mutableStateOf(true) }
            var animationStateExpanded by remember { mutableStateOf(false) }

            if (expandedState) {

                customCalendar(expandedState, updateDay = {
                    updateDay(it)
                })


            } else {


                customCalendar(expandedState) {
                    updateDay(it)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    if (expandedState) {
                        Icon(
                            imageVector = Icons.Filled.CalendarViewMonth,
                            contentDescription = "switch to month view"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.CalendarViewWeek,
                            contentDescription = "switch to week view"
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun ComposeDemo(
) {
    Column(

    ) {

    }
    Row (

            ){

    }
    Card(

    ) {

    }
    Box() {

    }

}