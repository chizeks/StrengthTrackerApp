package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class FrontLever(

    name: String = "Front Lever",
    holdTime: String = "",
    weight: String = "",
    sir: String = "",
    progression: String = "",
    notes: String = "",
    setNumber: Long = -1,
    exType: String = "static"
) : Statics(
    name = name,
    weight = weight,
    holdTime = holdTime,
    progression = progression,
    notes = notes,
    sir = sir,
    setNumber = setNumber
) {
    override var name = name
    override var holdTime = holdTime
    override var weight = weight
    override var sir = sir
    override var progression = progression
    override var notes = notes
    override var setNumber = setNumber
    override var exType = exType
}
