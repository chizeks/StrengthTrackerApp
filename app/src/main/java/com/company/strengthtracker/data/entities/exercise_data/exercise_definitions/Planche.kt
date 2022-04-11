package com.company.strengthtracker.data.entities.exercise_data.exercise_definitions

import com.company.strengthtracker.data.entities.exercise_data.main_categories.Statics

class Planche (
    uid: String,
    holdTime: String,
    weight: String,
    sir: String,
    progression: String,
    override val name: String = "Planche"
): Statics {
    override val uid: String = uid
    override val holdTime: String = holdTime
    override val weight: String = weight
    override val sir: String = sir
    override val progression: String = progression
}