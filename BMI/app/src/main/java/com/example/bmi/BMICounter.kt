package com.example.bmi
import kotlin.math.round

class BMICounter {
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
        //TODO remove magic numbers
        if (isImperial){
             return (0.453592*mass > 20) and (0.453592*mass < 300) and (2.54*height > 100) and (2.54*height < 250)
        }
        return (mass > 20) and (mass < 300) and (height > 100) and (height < 250)
    }
}