package com.example.bmi.logic

import com.example.bmi.R


class BMIResult(val color: Int, val category: CategoryEnum, val details: Int) {
    companion object {
        fun bmiResult(bmi: Double): BMIResult {
            var result = BMIResult(0, CategoryEnum.Starvation, 0)
            when {
                bmi < 17.0 -> {
                    result = BMIResult(R.color.st_tropaz, CategoryEnum.Starvation, R.string.norm_info_text_emaciation)
                }
                bmi < 18.5 -> {
                    result = BMIResult(R.color.persian_green, CategoryEnum.Underweight, R.string.norm_info_text_underweight)
                }
                bmi < 25.0 -> {
                    result = BMIResult(R.color.japanese_laurel, CategoryEnum.Normal, R.string.norm_info_text_normal)
                }
                bmi < 30.0 -> {
                    result = BMIResult(R.color.indochine, CategoryEnum.Overweight, R.string.norm_info_text_overweight)
                }
                bmi >= 30.0 -> {
                    result = BMIResult(R.color.guardsman_red, CategoryEnum.Obese, R.string.norm_info_text_obese)
                }
            }
            return result
        }
    }
}
