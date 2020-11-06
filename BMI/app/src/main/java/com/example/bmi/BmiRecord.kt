package com.example.bmi

import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class BmiRecord(val bmi:Double, val height: Double, val mass:Double, val isImperial:Boolean,  val time: Long){
    //TODO: strings
    override fun toString(): String{
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
        return sdf.format(Date(Timestamp(time).time)) + " result was: " + bmi.toString()
    }
}