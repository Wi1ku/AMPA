package com.example.bmi
import kotlin.math.round

class BMICounter {
    val lbsToKliogramsRatio = 0.453592
    val inchToMeterRatio = 2.54
    //bounds are in metric units, mass in kg, height in cm
    val massBottomBound = 20
    val massTopBound = 300
    val heightBottomBound = 100
    val heightTopBound = 250

    //mass - mass in kg/lbs, height in inches/centimeters
    fun countBmi(mass: Double, height: Double, isImperial: Boolean = false): Double {
        val bmi = mass/(height*height);
        return if (isImperial){
            (bmi*703).round(2);
        } else (bmi*10000).round(2)
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    fun checkForCorrectValues(mass: Double, height: Double, isImperial: Boolean = false): Boolean{
        if (isImperial){
             return (lbsToKliogramsRatio*mass > massBottomBound) and (lbsToKliogramsRatio*mass < massTopBound) and (inchToMeterRatio*height > heightBottomBound) and (inchToMeterRatio*height < heightTopBound)
        }
        return (mass > massBottomBound) and (mass < massTopBound) and (height > heightBottomBound) and (height < heightTopBound)
    }
}