package com.company.strengthtracker.presentation.test_screen.graph_utils

import androidx.compose.ui.geometry.Offset

class CoordinateFormatter() {
    //formula for scaling coordinates points (x, y) to new values that will result in a graph that fills the canvas height and width evenly
    fun getCoordList(
        listX: List<Float>,
        listY: List<Float>,
        yMax: Float,
        xMax: Float,
        yMin: Float,
        xMin: Float,
        height: Float,
        width: Float,
        padding: Float //provides a right shift modifier that can be scaled and applied to an axis
    ): MutableList<Offset> {
        val coordinateList: MutableList<Offset> = mutableListOf()
        //if (listX.size == listY.size) {
        listY.forEachIndexed { i, it ->
            coordinateList.add(
                Offset(
                    x = ((listX[i]) * (width / xMax)) +
                            (padding - (xMin * (width / xMax))), //scaling and applying right shift to x to fit to graph axis'
                    y = ((yMax - it) * (height / yMax))
                )
            )
        }
        //      }
        return coordinateList
    }

    fun getComparisonCoordList(
        xListInitial: List<Float>,
        xListCurrent: List<Float>,
        yListInitial: List<Float>,
        yListCurrent: List<Float>,
        totalYMax: Float,
        totalXMax: Float,
        totalXMin: Float,
        height: Float,
        width: Float,
        padding: Float,
    ): MutableList<MutableList<Offset>> {
        val coordinatesInitial: MutableList<Offset> = mutableListOf()
        val coordinatesCurrent: MutableList<Offset> = mutableListOf()
        val comparedList: MutableList<MutableList<Offset>> = mutableListOf()
        yListCurrent.forEachIndexed { i, it ->
            coordinatesCurrent.add(
                Offset(
                    x = ((xListCurrent[i] * (width / totalXMax))) + (padding - (totalXMin * (width / totalXMax))),
                    y = ((totalYMax - it) * (height / totalYMax))
                )
            )

        }
        comparedList.add(0, coordinatesCurrent)

        yListInitial.forEachIndexed { i, it ->
            coordinatesCurrent.add(
                Offset(
                    x = ((xListInitial[i] * (width / totalXMax))) + (padding - (totalXMin * (width / totalXMax))),
                    y = ((totalYMax - it) * (height / totalYMax))
                )
            )

        }
        comparedList.add(1, coordinatesInitial)
        return comparedList
    }
    //returns min of non-null float list, values dont need to be normalized,
//    fun min(list: List<Float>): Float {
//        var min = Float.MAX_VALUE
//        for (i in list.indices) {
//            if (min > list[i]) min = list[i]
//        }
//        return min
//    }

}

