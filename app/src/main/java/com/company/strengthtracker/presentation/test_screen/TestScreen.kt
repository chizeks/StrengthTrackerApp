package com.company.strengthtracker.presentation.test_screen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.TextDelegate.Companion.paint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    navController: NavController,
    viewModel: TestViewModel = hiltViewModel()
) {
    val listX: List<Float> =
        listOf(1f, 3f, 5f, 7f, 9f, 11f, 13f, 15f, 17f, 19f, 21f, 23f, 25f, 27f, 29f, 31f)
    val listY: List<Float> =
        listOf(25f, 25f, 30f, 35f, 40f, 45f, 52.5f, 50f, 50f, 52.5f, 55f, 50f, 60f, 60f, 65f, 65f)
    val yMax = max(listY)
    val xMax = max(listX)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Graph(listX = listX, listY = listY, yMax = yMax, xMax = xMax)
    }
}

/*x_0 = scaledXDist, x_1 += scaledXDist
* y = (yMax - y)*(height/yMax)*/
@Composable
fun Graph(
    listX: List<Float>,
    listY: List<Float>,
    yMax: Float,
    xMax: Float,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(350.dp)
            .width(350.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize())  {
            val width = size.width
            val height = size.height
            drawLine(
                start = Offset(0f, ((yMax - 0) * (height / yMax)).toFloat()),
                end = Offset(width, ((yMax - 0) * (height / yMax)).toFloat()),
                color = Color.Black,
                strokeWidth = 5f
            )


            drawLine(
                start = Offset(0f, ((yMax - 0) * (height / yMax))),
                end = Offset(0f, (yMax - (yMax + 2)) * (height / yMax)),
                color = Color.Black,
                strokeWidth = 5f
            )
            var coordinateList: MutableList<Offset> = mutableListOf()
            listY.forEachIndexed { i, it ->
                coordinateList.add(
                    Offset(
                        x = listX[i] * (width / xMax),
                        y = ((yMax - it) * (height / yMax))
                    )
                )
//                cList.add(
//                    Offset(
//                        x = listX[i + 1] * (width / xMax),
//                        y = ((yMax - listY[i + 1]) * (height / yMax))
//                    )
//                )
            }

            for(i in listX.indices){
                drawContext.canvas.nativeCanvas.drawText(
                )
            }

            for (i in listY.indices) {
                if (i + 1 < 16) {
                    val x1 = ((listX[i]) * (width / xMax))
                    val y1 = ((yMax - listY[i]) * (height / yMax))
                    val x2 = ((listX[i + 1]) * (width / xMax))
                    val y2 = ((yMax - listY[i + 1]) * (height / yMax))
                    drawCircle(
                        color = Color.Black,
                        center = Offset(
                            x = x1,
                            y = y1
                        ),
                        radius = 8f
                    )


                    drawLine(
                        color = Color.Black,
                        start = Offset(x1, y1),
                        end = Offset(x2, y2),
                        strokeWidth = 5f
                    )
//                    drawCircle(
//                        color = Color.Black,
//                        center = Offset(
//                            x2, y2
//                        ),
//                        radius = 8f
//                    )
                }
            }
        }
    }
}


fun max(list: List<Float>): Float {
    var max = 0f
    list.forEach {
        if (it > max)
            max = it
    }
    return max
}