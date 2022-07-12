package com.company.strengthtracker.presentation.progress_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.util.*

@Composable
fun ProgressScreen(
    navController: NavController,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val yStep = 50
    val random = Random()
    /* to test with random points */
    val points = (0..9).map {
        var num = random.nextInt(350)
        if (num <= 50)
            num += 100
        num.toFloat()
    }
    /* to test with fixed points */
//                val points = listOf(150f,100f,250f,200f,330f,300f,90f,120f,285f,199f),
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Graph(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            xValues = (0..9).map { it + 1 },
            yValues = (0..6).map { (it + 1) * yStep },
            points = points,
            paddingSpace = 16.dp,
            verticalStep = yStep
        )
    }
}

@Composable
fun Graph(
    modifier: Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
            val yAxisSpace = size.height / yValues.size
            /** placing x axis points */
            /** placing our x axis points */
            for (i in points.indices) {
                val x1 = xAxisSpace * xValues[i]
                val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                /** drawing circles to indicate all the points */
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(x1, y1)
                )
            }
        }
    }
}
//@Composable
//fun GraphMainView(colors: ProgressViewModel.Colors) {
//
//    Column(modifier = Modifier.fillMaxSize(0.9f)) {
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//        ) {
//            val canvasHeight = size.height
//            val canvasWidth = size.width
//val verticalStep = 50f
//            val xAxisSpace = (size.width)/6
//            val yAxisSpace = (size.height)/5
//            drawPoints(
//                listOf(
//                    Offset(x = xAxisSpace * 1f, y = size.height - (yAxisSpace) / 1f),
//                    Offset(x = xAxisSpace * 2f, y = size.height - (yAxisSpace) / 1.5f),
//                    Offset(x = xAxisSpace * 3f, y = size.height - (yAxisSpace) / 1.51f),
//                    Offset(x = xAxisSpace * 4f, y = size.height - (yAxisSpace) / 2f),
//                    Offset(x = xAxisSpace * 5f, y = size.height - (yAxisSpace) / 4f),
//                ),
//                pointMode = PointMode.Lines,
//                strokeWidth = Stroke.DefaultMiter,
//                colorFilter = ColorFilter.tint(color = Color.Black),
//                brush = Brush.linearGradient(listOf(Color.Black, Color.Black))
//            )
////            drawLine(
////                start = Offset(x = canvasWidth, y = 0f),
////                end = Offset(x = 0f, y = canvasHeight),
////                color = Color.Black
////            )
//        }
//    }
//}

//@Preview
//@Composable
//fun GraphPreview() {
//    GraphMainView(colors = ProgressViewModel.Colors())
//}