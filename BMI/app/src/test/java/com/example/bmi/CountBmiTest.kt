package com.example.bmi

import org.junit.Assert
import org.junit.Test

class CountBmiTest {
    @Test
    fun randomValuesInboundsTestMetric(){
        val BMICounter = BMICounter()
        Assert.assertEquals(18.5, BMICounter.countBmi(60.0,180.0), 0.05)
        Assert.assertEquals(24.2, BMICounter.countBmi(69.696969,169.69), 0.05)
        Assert.assertEquals(8.3, BMICounter.countBmi(40.0,220.0), 0.05)
        Assert.assertEquals(38.16, BMICounter.countBmi(125.12,181.1), 0.05)
        Assert.assertEquals(49.0, BMICounter.countBmi(150.01,175.0), 0.05)
        Assert.assertEquals(3.6, BMICounter.countBmi(21.0,240.0), 0.05)
    }

    @Test
    fun randomValuesInboundsTestImperial(){
        val BMICounter = BMICounter()
        Assert.assertEquals(18.5, BMICounter.countBmi(132.27,70.86, isImperial = true), 0.05)
        Assert.assertEquals(24.2, BMICounter.countBmi(153.655,66.8070, isImperial = true), 0.05)
        Assert.assertEquals(8.3, BMICounter.countBmi(88.1849,86.61, isImperial = true), 0.05)
        Assert.assertEquals(38.16, BMICounter.countBmi(275.842,71.29, isImperial = true), 0.05)
        Assert.assertEquals(49.0, BMICounter.countBmi(330.71,68.89, isImperial = true), 0.05)
        Assert.assertEquals(3.6, BMICounter.countBmi(46.29,94.4882, isImperial = true), 0.05)
    }

    @Test
    fun boundsTestMetric(){
        val BMICounter = BMICounter()
        Assert.assertEquals(BMICounter.checkForCorrectValues(60.0,180.0), true)
        Assert.assertEquals(BMICounter.checkForCorrectValues(19.19,180.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(300.0,180.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(60.0,90.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(60.0,300.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(0.0,180.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(60.0,0.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(0.0,0.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(-60.0,180.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(-60.0,-180.0), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(60.0,-180.0), false)
    }

    @Test
    fun boundsTestImperial(){
        val BMICounter = BMICounter()
        Assert.assertEquals(BMICounter.checkForCorrectValues(132.27,70.86, isImperial = true), true)
        Assert.assertEquals(BMICounter.checkForCorrectValues(32.27,70.86, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(1000.0,70.86, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(132.27,20.00, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(132.27,170.86, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(0.0,70.86, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(132.27,0.0, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(0.0,0.0, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(-132.27,70.86, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(-132.27,-70.86, isImperial = true), false)
        Assert.assertEquals(BMICounter.checkForCorrectValues(132.27,-70.86, isImperial = true), false)
    }
}