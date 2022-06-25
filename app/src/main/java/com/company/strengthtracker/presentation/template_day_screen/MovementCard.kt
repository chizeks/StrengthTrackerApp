package com.company.strengthtracker.presentation.template_day_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics
import com.company.strengthtracker.ui.theme.Shapes
import java.time.LocalDate

//Will hold exercise title and relevant information as it is logged
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableExerciseCard(
    movement: AllExercises,
    titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
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