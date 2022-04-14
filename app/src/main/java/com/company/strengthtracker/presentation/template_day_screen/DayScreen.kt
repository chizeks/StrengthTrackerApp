package com.company.strengthtracker.presentation.template_day_screen

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.view.FrameMetrics.ANIMATION_DURATION
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.*
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.ui.theme.Shapes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel()

) {
    var date: LocalDate = LocalDate.now()
    var dateString by remember { mutableStateOf(date.toString()) }
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Select")
            negativeButton("Close")
        }
    ) {
        datepicker {
            date = it
        }
    }
//    val date = "1-1-2022"
//    Column(
//        modifier = Modifier
//            .fillMaxSize().verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp)) {
//            Button(
//                onClick = {},
//                elevation = ButtonDefaults.elevation(5.dp),
//                modifier = Modifier.shadow(5.dp)
//            ) {
//                Text(text = date)
//            }
//        }
//
//        ExpandableCardStaticExercise(Planche("", "", "", "", "", ""))
//        ExpandableCardStaticExercise(HandstandPushup("","","","",""))
//    }
    val list = remember {
        mutableStateListOf<AllExercises>()
    }
    val bruhList = mutableListOf<AllExercises>()
    bruhList.add(Planche())
    bruhList.add(HandstandPushup())
    bruhList.add(PullUps())
    bruhList.add(Squat())
    bruhList.add(FrontLever())

    var newItem by remember { mutableStateOf("") }
    var expandedPicker by remember { mutableStateOf(false) }
    var expandedNotes by remember { mutableStateOf(false) }
    var dailyNotes by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.2f)) {
                Image(
                    painter = painterResource(id = R.drawable.gigachad),
                    contentDescription = "Giga Chad"
                )
            }
            Button(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = {
                    dialogState.show()
                })
            {
                Text(text = "Calendar")
            }

            Button(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = {
                    expandedNotes = true
                })
            {
                Text(text = "Notes")
            }
            Button(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = {
                    expandedPicker = true
                })
            {
                Text(text = "+")
            }

        }
        Column(
            modifier= Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            DropdownMenu(
                modifier = Modifier.fillMaxSize(0.9f),
                expanded = expandedNotes,
                onDismissRequest = { expandedNotes = false },
                offset = DpOffset(20.dp, 10.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp),
                    value = dailyNotes,
                    onValueChange = {
                        dailyNotes = it
                    },
                    label = { Text("Notes") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium
                )
            }

            DropdownMenu(
                expanded = expandedPicker,
                onDismissRequest = { expandedPicker = false }
            ) {
                bruhList.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        list.add(bruhList.get(index))

                    }) {
                        Text(text = bruhList.get(index).name)
                    }
                }
            }

        }

        DraggableCard(isRevealed = false, cardOffset = 20f, onExpand = { /*TODO*/ }) {

        }
        LazyColumn() {
            items(items = list) { movement ->
                ExpandableExerciseCard(movement = movement)
            }
        }
    }
}


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {
    val offsetX = remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState)
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset - offsetX.value else -offsetX.value },
    )
    var test by remember {mutableStateOf("")}
    Card(
        modifier = Modifier
            .offset { IntOffset((offsetX.value + offsetTransition).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX.value, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onExpand()
                        return@detectVerticalDragGestures
                    } else if (newValue.x <= 0) {
                        onCollapse()
                        return@detectVerticalDragGestures
                    }
                    change.consumePositionChange()
                    offsetX.value = newValue.x
                }
            },

        content = { TextField(value = test, onValueChange = {test = it}) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownCustomSet(
    list: MutableList<AllExercises>
) {
    LazyColumn() {
        items(items = list) { movement ->
            DropDownCustomItem(movement = movement)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownCustomItem(
    movement: AllExercises,

    ) {
    Card(
        modifier = Modifier.fillMaxWidth(0.95f),
        onClick = {}
    ) {
        Text(text = movement.name)
    }
}

@ExperimentalMaterialApi
@Composable
fun ExpandableExerciseCard(
    movement: AllExercises,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    width: Float = 1.0f,
    padding: Dp = 10.dp
) {
    val title = movement.name
    var expandedState by remember { mutableStateOf(false) }
    var testString by remember { mutableStateOf("") }

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
            .shadow(10.dp),
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
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-down arrow"
                    )
                }
            }


            var reps by remember { mutableStateOf("") }
            var dWeight by remember { mutableStateOf("") }
            var rir by remember { mutableStateOf("") }
            var notes by remember { mutableStateOf(movement.notes) }

            if (expandedState) {

                if (movement is Statics) {
                    StaticsTextFields(movement = movement)
                } else if (movement is Dynamics) {
                    DynamicsTextFields(movement = movement)
                }
            }
        }
    }
}

@Composable
fun DynamicsTextFields(movement: Dynamics) {
    var reps by remember { mutableStateOf(movement.reps) }
    var dWeight by remember { mutableStateOf(movement.weight) }
    var rir by remember { mutableStateOf(movement.rir) }
    var notes by remember { mutableStateOf(movement.notes) }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = reps,
        onValueChange = {
            reps = it
        },
        label = { Text("reps / Set") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = dWeight,
        onValueChange = {
            dWeight = it
        },
        label = { Text("Weight / Set") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = rir,
        onValueChange = {
            rir = it
        },
        label = { Text("RIR / Set") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        value = notes,
        onValueChange = {
            notes = it
        },
        label = { Text("Notes") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )
}


@Composable
fun StaticsTextFields(movement: Statics) {

    var notes by remember { mutableStateOf(movement.notes) }
    var sHoldTime by remember { mutableStateOf(movement.holdTime) }
    var sWeight by remember { mutableStateOf(movement.weight) }
    var sir by remember { mutableStateOf(movement.sir) }
    var progression by remember { mutableStateOf(movement.progression) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextField(
            modifier = Modifier
                .weight(2f)
                .padding(1.dp),
            value = sHoldTime,
            onValueChange = {
                sHoldTime = it
                movement.holdTime = it //gotta do this to others
            },
            label = { Text("Hold time / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
        TextField(
            modifier = Modifier
                .weight(2f)
                .padding(1.dp),
            value = sWeight,
            onValueChange = {
                sWeight = it
            },
            label = { Text("Weight / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    )
    {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(1.dp),
            value = sir,
            onValueChange = {
                sir = it
            },
            label = { Text("SIR / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(1.dp),
            value = progression,
            onValueChange = {
                progression = it
            },
            label = { Text("Progression / Set") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium
        )
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .height(200.dp),
        value = notes,
        maxLines = 5,
        //singleLine = true,
        onValueChange = {
            notes = it
            movement.notes = it
        },
        label = { Text("Notes") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium
    )

}
