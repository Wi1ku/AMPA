package com.example.bmi

import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class BmiRecord(val bmi:Double, val height: Double, val mass:Double, val isImperial:Boolean,  val time: Long){
    fun getTime(): String{
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
        return "Record from " + sdf.format(Date(Timestamp(time).time)).toString()
    }

    fun getResult(): String {
        return "Result was: $bmi"
    }

    fun getMeasures(): String {
        return "Mass: " + mass + when (isImperial) {
            true -> "[lbs]"
            false -> "[kg]"
        } + "\nHeight: " + height + when (isImperial) {
            true -> "[inch]"
            false -> "[cm]"
        }
    }
}