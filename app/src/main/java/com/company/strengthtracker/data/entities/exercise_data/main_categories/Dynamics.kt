package com.company.strengthtracker.data.entities.exercise_data.main_categories
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState.DYNAMIC
import com.company.strengthtracker.data.entities.exercise_data.main_categories.ExState.STATIC
open class Dynamics(
    name:String = "",
    weight:String = "",
    reps:String = "",
    rir: String = "",
    setNumber:Long = -1,
    exType:ExState = DYNAMIC
):AllExercises(name=name, weight = weight, setNumber = setNumber) {
    override var name = name
    override var weight = weight
    open var reps = reps
    open var rir = rir
    override var setNumber = setNumber
}