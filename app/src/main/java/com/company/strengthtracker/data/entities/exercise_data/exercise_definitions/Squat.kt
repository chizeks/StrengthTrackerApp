package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics
import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class Squat (override var name: String = "Squat"
): Dynamics() {
    override var reps: String = ""
    override var weight: String = ""
    override var rir: String = ""
    override var notes: String = ""
    override var setNumber:Int = -1
    val exType = "dynamic"
    constructor(
        aid: String,
        reps: String,
        weight: String,
        rir: String,
        notes: String,

        ) : this() {
        this.reps = reps
        this.weight = weight
        this.rir = rir
        this.notes = notes
    }
}