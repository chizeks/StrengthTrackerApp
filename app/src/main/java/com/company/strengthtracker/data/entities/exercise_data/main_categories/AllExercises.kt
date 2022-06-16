package com.company.strengthtracker.data.entities.exercise_data.main_categories

open abstract class AllExercises constructor(
    name: String,
    notes: String,
    weight: String,
    setNumber:Long
) {
    open var name = name
    open var notes = notes
    open var weight = weight
    open var setNumber = setNumber

//    abstract var name:String
//    abstract var notes:String
//    abstract var weight:String
//    abstract var setNumber: Int
}