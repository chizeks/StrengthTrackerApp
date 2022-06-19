package com.company.strengthtracker.presentation.template_day_screen

import android.os.Build
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
import com.company.strengthtracker.data.entities.exercise_data.main_categories.TypeDictionary
import com.company.strengthtracker.ui.theme.*
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.theme.KalendarShape

import java.time.LocalDate
import com.himanshoe.kalendar.ui.Kalendar
import com.himanshoe.kalendar.ui.KalendarType

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel(),
) {
    val typeList = remember {viewModel.exerciseTypes}
    val exList = remember {viewModel.exList}


    //State reference
    val screenState by remember { viewModel.dayScreenState }
    //holds date on select from calendar and on open app.
    var dateString = remember { viewModel.date.value.toString() }
    var date = remember {viewModel.dateIn}
    //holder for passing data back to UI from viewmodel
    var exerciseBundle = remember { viewModel.exerciseBundleMain }

    //Controls Calendar state
    var exState by remember { mutableStateOf(false) }

    //holder for main screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) { //fades in/out child elements

        when (screenState) {
            DayViewModel.DayScreenState.LAUNCH -> {
                TopAppBar(viewModel = viewModel, dateString = dateString)

//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.SpaceEvenly,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    exerciseBundle.forEachIndexed { index, element ->
//                            ExpandableExerciseCard(
//                                movement = element.get(0),
//                                date = date,
//                                exercises = exerciseBundle.get(index)
//                            )
//                    }
//                }
            }
            DayViewModel.DayScreenState.LOADING -> {
                Text(text = "Loading")
            }
            DayViewModel.DayScreenState.LOADED -> {

                TopAppBar( viewModel = viewModel, dateString = dateString)

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
            }
            DayViewModel.DayScreenState.SELECT -> {
                viewModel.filterTypeList()
                SelectionColumn(exerciseList = typeList.value, viewModel = viewModel)
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopAppBar(
    viewModel:DayViewModel,
    dateString:String
){
    var exState by remember { mutableStateOf(false) }

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
                .alpha(ContentAlpha.medium)
                .weight(1f),
            onClick = {
                exState = !exState
            }
        ) {
            Icon(
                imageVector = Icons.Sharp.CalendarViewWeek,
                contentDescription = "switch to month view"
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
        Text(dateString)
        //does nothing currently
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .weight(1f),
            onClick = {
                viewModel.openSelection()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "selectionview"
            )

        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectionColumn(
    exerciseList:List<AllExercises>,
    viewModel: DayViewModel
){
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top, ){
        exerciseList.forEach{ it
            SelectionCard(movement = it, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectionCard(
    movement:AllExercises,
    viewModel: DayViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(true, onClick = {
                viewModel.exerciseBundleMain.add(mutableStateListOf(movement))
                viewModel.closeSelection()
            })
        ) {
            Text(movement.name, fontSize = 25.sp, modifier = Modifier.padding(50.dp))
        }
    }
}

//Will hold exercise title and relevant information as it is logged
@ExperimentalMaterialApi
@Composable
fun ExpandableExerciseCard(
    movement: AllExercises,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    width: Float = 1.0f,
    padding: Dp = 10.dp,
    date: LocalDate,
    exercises: MutableList<AllExercises>
) {
    var bruh: Long = 0
    //for tracking how many sets have been logged, just increments in a lambda
    var setsSoFar by remember { mutableStateOf(bruh) }

    //card title
    val title = movement.name

    //managed expanded state of the set log pop up
    var expandedState by remember { mutableStateOf(false) }
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
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                //test widget
                Text(
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.alpha(alpha = 0.8f)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.99f)
                        .padding(start = 0.dp, top = 10.dp, bottom = 5.dp, end = 0.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(0.99f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        "Set No.",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(0.98f),
                        textAlign = TextAlign.Start
                    )

                    Text(
                        "Lever",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1.0f),
                        textAlign = TextAlign.Start
                    )

                    Text(
                        "Hold Time",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1.4f),
                        textAlign = TextAlign.Start
                    )

                    Text(
                        "Assist",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1.1f),
                        textAlign = TextAlign.Start
                    )

                    Text(
                        "SiR",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(0.9f),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Column{
                    exercises.forEach { exercise ->
                        CondensedSetRow(movement = exercise)
                    }
                }
            }

            //popup controller, will have different popups maybe? not sure
            if (expandedState) {

                if (movement is Statics) {

                    StaticsAddSetPopUp(
                        movement = movement,
                        date = date,
                        setsSoFar = setsSoFar
                    ) { setsSoFar = it }
                    //StaticsTextFields(movement = movement)
                } else if (movement is Dynamics) {
                    //  DynamicsTextFields(movement = movement)
                }
            }
        }
    }
}

@Composable
fun CondensedSetRow(
    movement: AllExercises
) {

    Row(
        modifier = Modifier.fillMaxWidth(0.99f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        if (movement is Statics) {
            Text(
                text = "",
                fontSize = 14.sp,
                modifier = Modifier.weight(0.98f),
                textAlign = TextAlign.Start
            )

            Text(
                movement.progression,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )

            Text(
                movement.holdTime,
                fontSize = 14.sp,
                modifier = Modifier.weight(1.4f),
                textAlign = TextAlign.Start
            )

            Text(
                movement.weight,
                fontSize = 14.sp,
                modifier = Modifier.weight(1.1f),
                textAlign = TextAlign.Start
            )

            Text(
                movement.sir,
                fontSize = 14.sp,
                modifier = Modifier.weight(0.9f),
                textAlign = TextAlign.Start
            )
        } else if (movement is Dynamics) {
            //TODO
        }
    }
}

@Composable
fun StaticsAddSetPopUp(
    movement: Statics,
    date: LocalDate,
    viewModel: DayViewModel = hiltViewModel(),
    setsSoFar: Long,
    updateSetNumber: (Long) -> Unit
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
                    ) {
                        textContentProgression = it
                        movement.progression = it
                    }
                    TimeIncrementTextField(
                        label = "Time",
                        movement = movement,
                        trailingLabel = "sec",
                        onDataChanged = {
                            textContentTime = it
                            movement.holdTime = it
                        }
                    )
                    WeightIncrementTextField(
                        movement = movement,
                        label = "Assistance",
                        trailingLabel = "kg."
                    ) {
                        textContentAssistance = it
                        movement.weight = it
                    }
                    TimeIncrementTextField(
                        movement = movement,
                        label = "SiR",
                        trailingLabel = "sec"
                    ) {
                        textContentSiR = it
                        movement.sir = it
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        //add set
                        Button(

                            onClick = {
                                updateSetNumber(setsSoFar + 1)
                                movement.setNumber = setsSoFar
                                /*PASS DATA TO VIEWMODEL, IMMEDIATELY MAKE NEW DOCUMENT*/
                                viewModel.addStaticsSet(
                                    movement
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

//custom TextField and +/- widgets
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


//same as weight, gotta combine these later
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


//Textfield with a dropdown menu, currently doesnt take a list as input but could add
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
