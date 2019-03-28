package com.example.bmi.logic

class BMIForLbIn(private var weight: Int, private var height: Int) : Bmi {

    override fun countBmi(): Double = (weight / (height.toDouble() * height)) * 703.0

    override fun isInRangeWeight(): Boolean {
        return (1763 > weight || weight > 66)
    }

    override fun isInRangeHeight(): Boolean {
        return (102 > height || height > 39)
    }
}