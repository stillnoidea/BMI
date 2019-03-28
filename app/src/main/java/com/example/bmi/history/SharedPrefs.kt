package com.example.bmi.history

import android.app.Activity
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class SharedPrefs {

    companion object {
        private const val prefs_key = "key"
        private const val values_key = "values"
        private const val number_of_items = 10

        fun saveBmi(
            weight: String,
            height: String,
            bmi: String,
            norm: String,
            isImperial: Boolean,
            activity: Activity
        ) {
            //getting all information that have to be saved in one String

            val time = Calendar.getInstance().time
            val id = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(time)
            val timeText = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(time)
            val weightUnit: String
            val heightUnit: String
            if (isImperial) {
                weightUnit = "lb"
                heightUnit = "in"
            } else {
                weightUnit = "kg"
                heightUnit = "cm"
            }

            val text = "$id,$weight $weightUnit,$height $heightUnit,$bmi,$norm,$timeText"

            //taking only number_of_items-1, so still in history will be 10 items, after take, adding the last counted value

            val sharedPrefs = activity.getSharedPreferences(prefs_key, Context.MODE_PRIVATE)
            val oldSet = sharedPrefs.getStringSet(values_key, mutableSetOf())
            var list: MutableList<String> = oldSet!!.toMutableList()
            list.sortDescending()
            list = list.take(number_of_items - 1).toMutableList()
            list.add(0, text)
            val newSet = list.toMutableSet()

            with(sharedPrefs.edit()) {
                //adding new set of strings to SharedPrefs, than using apply, because it will make save action in the background
                putStringSet(values_key, newSet)
                apply()
            }
        }

        fun loadBmi(activity: Activity): MutableSet<String> {
            val sharedPrefs = activity.getSharedPreferences(prefs_key, Context.MODE_PRIVATE)
            return sharedPrefs.getStringSet(values_key, mutableSetOf())!!
        }

        fun isEmpty(activity: Activity): Boolean {
            val sharedPrefs = activity.getSharedPreferences(prefs_key, Context.MODE_PRIVATE)
            val set = sharedPrefs.getStringSet(values_key, mutableSetOf())
            return set!!.size == 0
        }
    }
}