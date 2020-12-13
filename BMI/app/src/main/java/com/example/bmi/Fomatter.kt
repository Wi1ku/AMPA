package com.example.bmi

import com.example.bmi.Room.BmiRecord
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Fomatter {
    companion object {
        fun getTime(time: Long): String{
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            return "Record from " + sdf.format(Date(Timestamp(time).time)).toString()
        }

        fun getResult(bmi: Double): String {
            return "Result was: $bmi"
        }

        fun getMeasures(record: BmiRecord): String {
            return "Mass: " + record.mass + when (record.isImperial) {
                true -> "[lbs]"
                false -> "[kg]"
            } + "\nHeight: " + record.height + when (record.isImperial) {
                true -> "[inch]"
                false -> "[cm]"
            }
        }
    }
}