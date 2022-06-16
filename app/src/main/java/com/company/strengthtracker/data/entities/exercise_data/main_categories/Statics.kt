package com.company.strengthtracker.data.entities.exercise_data.main_categories

import com.google.common.math.IntMath

open class Statics(
    name:String = "",
    holdTime:String = "",
    weight:String = "",
    sir:String = "",
    progression:String = "",
    setNumber:Long = 1,
    notes:String = "",
    exType: String = "static"
):AllExercises(name=name, notes = notes, weight = weight, setNumber = setNumber) {
    override var name = name
    open var holdTime = holdTime
    override var weight = weight
    open var sir = sir
    open var progression = progression
    override var setNumber = setNumber
    override var notes = notes
    open var exType = exType
}