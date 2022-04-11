package com.company.strengthtracker.presentation.template_day_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.Planche
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel()
) {
    val date = "1-1-2022"
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Button (onClick ={},
                elevation = ButtonDefaults.elevation(5.dp),
                modifier = Modifier.shadow(5.dp)
            ){
                Text(text = date)
            }
        }

        ExpandableCardStaticExercise(Planche("", "", "", "", ""))
    }

}

@ExperimentalMaterialApi
@Composable
//Static as in static movement not in programming sense
fun ExpandableCardStaticExercise(
    movement: Statics,
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
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
            var sHoldTime by remember { mutableStateOf(movement.holdTime) }
            var sWeight by remember { mutableStateOf(movement.weight) }
            var sir by remember { mutableStateOf(movement.sir) }
            var progression by remember { mutableStateOf(movement.progression) }
            var notes by remember { mutableStateOf("") }

            if (expandedState) {
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
            }
        }
    }
}

