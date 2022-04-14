package com.company.strengthtracker.presentation.template_day_screen

import android.content.res.Resources
import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
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

    val inputvalue by remember { mutableStateOf("") }
    var newItem by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
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
            Button(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                onClick = {
                    dialogState.show()
                })
            {
                Text(text = "Calendar")
            }
            Box(modifier = Modifier.fillMaxWidth(0.2f)) {
                Image(
                    painter = painterResource(id = R.drawable.gigachad),
                    contentDescription = "Giga Chad"
                )
            }
            Button(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                onClick = {
                    expanded = true
                })
            {
                Text(text = "Add")
            }


        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
                bruhList.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {list.add(bruhList.get(index))

                    }) {
                        Text(text = bruhList.get(index).name)
                    }
                }
        }

        LazyColumn() {
            items(items = list) { movement ->
                ExpandableExerciseCard(movement = movement)
            }
        }
    }
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
//Static as in static movement not in programming sense
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
                        var notes by remember { mutableStateOf(movement.notes) }
                        var sHoldTime by remember { mutableStateOf(movement.holdTime) }
                        var sWeight by remember { mutableStateOf(movement.weight) }
                        var sir by remember { mutableStateOf(movement.sir) }
                        var progression by remember { mutableStateOf(movement.progression) }

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = sHoldTime,
                            onValueChange = {
                                sHoldTime = it
                            },
                            label = { Text("Hold Time / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = sWeight,
                            onValueChange = {
                                sWeight = it
                            },
                            label = { Text("Weight / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = sir,
                            onValueChange = {
                                sir = it
                            },
                            label = { Text("SIR / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = progression,
                            onValueChange = {
                                progression = it
                            },
                            label = { Text("Progression / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = notes,
                            onValueChange = {
                                notes = it
                            },
                            label = { Text("Notes") }
                        )
                    } else if (movement is Dynamics) {

                        var reps by remember { mutableStateOf(movement.reps) }
                        var dWeight by remember { mutableStateOf(movement.weight) }
                        var rir by remember { mutableStateOf(movement.rir) }
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = reps,
                            onValueChange = {
                                reps = it
                            },
                            label = { Text("reps / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = dWeight,
                            onValueChange = {
                                dWeight = it
                            },
                            label = { Text("Weight / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = rir,
                            onValueChange = {
                                rir = it
                            },
                            label = { Text("RIR / Set") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = rir,
                            onValueChange = {
                                rir = it
                            },
                            label = { Text("Notes") }
                        )
                    }
                }
            }
        }
    }
