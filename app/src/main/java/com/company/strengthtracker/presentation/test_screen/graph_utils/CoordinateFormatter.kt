package com.company.strengthtracker.presentation.test_screen.graph_utils

import androidx.compose.ui.geometry.Offset

class CoordinateFormatter {
    //formula for scaling coordinates points (x, y) to new values that will result in a graph that fills the canvas height and width evenly
    fun getCoordList(
        listX: List<Float>,
        listY: List<Float>,
        yMax: Float,
        xMax: Float,
        height: Float,
        width: Float,
        padding: Float //provides a right shift modifier that can be scaled and applied to an axis
    ): MutableList<Offset> {
        val coordinateList: MutableList<Offset> = mutableListOf()
        val xMin = min(listX)
        listY.forEachIndexed { i, it ->
            coordinateList.add(
                Offset(
                    x = ((listX[i]) * (width / xMax)) +
                            (padding - (xMin * (width / xMax))), //scaling and applying right shift to x to fit to graph axis'
                    y = ((yMax - it) * (height / yMax))
                )
            )
        }
        return coordinateList
    }


    //returns min of non-null float list, values dont need to be normalized,
    fun min(list: List<Float>): Float {
        var min = Float.MAX_VALUE
        for (i in list.indices) {
            if (min > list[i]) min = list[i]
        }
        return min
    }

}

