package com.example.bmi

import org.junit.Assert
import org.junit.Test

class CountBmiTest {
    @Test
    fun test(){
        val BMICounter = BMICounter()
        Assert.assertEquals(18.5, BMICounter.countBmi(60.0,180.0), 0.05)
    }
}