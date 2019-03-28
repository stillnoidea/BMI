package com.example.bmi.logic

class BMiForKgCm(private var weight: Int, private var height: Int) : Bmi {

    override fun countBmi(): Double = weight * 10000.0 / (height * height)

    override fun isInRangeWeight(): Boolean {
        return (weight in 31..799)
    }

    override fun isInRangeHeight(): Boolean {
        return (height in 101..259)
    }

}


