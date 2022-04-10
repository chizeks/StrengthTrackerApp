package com.company.strengthtracker.presentation.template_day_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun DayScreen(
    navController: NavController,
    viewModel: DayViewModel = hiltViewModel()
) {
    ExpandableCardStaticExercise("Planche", true)
}

@ExperimentalMaterialApi
@Composable
fun ExpandableCardStaticExercise(
    title: String,
    category: Boolean,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    static
) {
    var expandedState by remember { mutableStateOf(false) }
    var testString by remember { mutableStateOf("") }
    var holdTimeXSet by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf(5) }

    val rotationState by animateFloatAsState(
        targetValue =
        if (expandedState) 180f
        else 0f
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            ),
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
            var sHoldTime by remember {mutableStateOf("")}
            var sWeight by remember {mutableStateOf("")}
            var sir by remember {mutableStateOf("")}
            var progression by remember {mutableStateOf("")}
            var notes by remember {mutableStateOf("")}

            if (expandedState) {
                if (category) {
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
                }
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

