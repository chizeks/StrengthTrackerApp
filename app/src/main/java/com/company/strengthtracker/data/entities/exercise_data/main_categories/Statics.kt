package com.company.strengthtracker.data.entities.exercise_data.main_categories

abstract class Statics:AllExercises() {
    override abstract val aid: String
    override abstract val name: String
    abstract val holdTime: String
    override abstract val weight: String
    abstract val sir: String
    abstract val progression: String
    override abstract val notes: String
}