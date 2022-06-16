package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class Planche(
    name: String = "Planche",
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
    setNumber = setNumber,
) {
    override var exType = exType

}
