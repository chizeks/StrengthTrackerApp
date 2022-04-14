package com.company.strengthtracker.data.entities.exercise_data.main_categories

abstract class Dynamics(

): AllExercises() {
    override abstract val aid:String
    override abstract val name:String
    override abstract val weight:String
    abstract val reps:String
    abstract val rir:String
    override abstract val notes:String
}