package com.company.strengthtracker.data.entities.exercise_data.main_categories

import com.company.strengthtracker.data.entities.exercise_data.exercise_definitions.*
import java.util.*

class TypeDictionary {
    val typeDictionary = mapOf(
        "Planche" to Planche(),
        "FrontLever" to FrontLever(),
        "HandstandPush" to HandstandPushup(),
        "PullUp" to PullUps(),
        "Squat" to Squat())
}