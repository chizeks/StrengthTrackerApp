package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class FrontLever(

    name: String = "Front Lever",
    holdTime: String = "",
    weight: String = "",
    sir: String = "",
    progression: String = "",
    setNumber: Long = -1,
    exType: ExState = ExState.STATIC
) : Statics(
    name = name,
    weight = weight,
    holdTime = holdTime,
    progression = progression,
    sir = sir,
    setNumber = setNumber
) {

}
