package com.example.bmi.logic

class BMiForKgCm(var weight: Int, var height: Int) : Bmi {

    override fun countBmi(): Double = weight * 10000.0 / (height * height)

    override fun isInRangeWeight():Boolean{
        return (800 > weight || weight < 30 )
    }

    override fun isInRangeHeight():Boolean{
        return ( 260 > height || height < 100)
    }

}


