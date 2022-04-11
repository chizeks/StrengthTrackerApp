package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Dynamics

class PullUps (override val name: String = "Pull ups"
): Dynamics() {
    override var aid: String = ""
    override var reps: String = ""
    override var weight: String = ""
    override var rir: String = ""
    override var notes: String = ""

    constructor(
        aid: String,
        reps: String,
        weight: String,
        rir: String,
        notes: String,

        ) : this() {
        this.aid = aid
        this.reps = reps
        this.weight = weight
        this.rir = rir
        this.notes = notes
    }
}