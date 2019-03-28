package com.example.bmi.logic

class BMIForLbIn(private var weight: Int, private var height: Int) : Bmi {

    override fun countBmi(): Double = (weight / (height.toDouble() * height)) * 703.0

    override fun isInRangeWeight(): Boolean {
        return (weight in 67..1762)
    }

    override fun isInRangeHeight(): Boolean {
        return (height in 40..101)
    }
}