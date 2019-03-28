package com.example.bmi.logic

interface Bmi {
    fun countBmi(): Double
    fun isInRangeWeight(): Boolean
    fun isInRangeHeight(): Boolean

}