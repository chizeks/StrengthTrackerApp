package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class FrontLever (

    override val name: String = "Front Lever"
): Statics() {
    override var aid: String = ""
    override var holdTime: String = ""
    override var weight: String = ""
    override var sir: String = ""
    override var progression:String = ""
    override var notes:String = ""
    constructor(
        aid: String,
        holdTime: String,
        weight: String,
        sir: String,
        progression: String,
        notes:String,
    ):this(){
        this.aid = aid
        this.holdTime = holdTime
        this.weight = weight
        this.sir = sir
        this.progression = progression
        this.notes = notes
    }
}
