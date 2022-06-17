package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class Squat (
    name: String = "Back squat",
    weight: String = "",
    rir:String = "",
    setNumber: Long = -1,
    exType: ExState = ExState.DYNAMIC,
    reps:String = ""
): Dynamics(name = name, weight = weight, reps = reps, rir = rir, setNumber = setNumber) {

}