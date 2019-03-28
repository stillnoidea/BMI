package com.example.bmi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.bmi.logic.BMIForLbIn
import com.example.bmi.logic.BMIResult.Companion.bmiResult
import com.example.bmi.logic.BMiForKgCm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var switchedUnitsForLbIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        main_button_count_bmi.setOnClickListener { countBmi() }
        main_button_bmi_norm.setOnClickListener { goToBmiNormAction() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_about -> {
                //show about page
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.action_switch_unit -> {
                //switch to imperial units if option in toolbar is not checked, else switch to si units
                if (switchedUnitsForLbIn) {
                    switchedUnitsForLbIn = true
                    item.isChecked = switchedUnitsForLbIn
                    switchToLbIn()
                } else {
                    switchedUnitsForLbIn = false
                    item.isChecked = switchedUnitsForLbIn
                    switchToKgCm()
                }
            }
            R.id.action_history -> {
                //show history page
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        //saving all needed values, so after rotation could be restored
        super.onSaveInstanceState(outState)
        Log.i(getString(R.string.main_save_log_tag), getString(R.string.main_save_log_message))

        val bmi = main_text_result_bmi.text
        outState?.putCharSequence(getString(R.string.main_save_key_bmi), bmi)

        val color = main_text_result_bmi.currentTextColor
        outState?.putInt(getString(R.string.main_save_key_bmi_color), color)

        val bmiNorm = main_text_bmi_norm.text
        outState?.putCharSequence(getString(R.string.main_save_key_norm), bmiNorm)

        val colorNorm = main_text_bmi_norm.currentTextColor
        outState?.putInt(getString(R.string.main_save_key_norm_color), colorNorm)

        val weightText = main_text_weight_label.text
        outState?.putCharSequence(getString(R.string.main_save_key_weight_text), weightText)

        val heightText = main_text_height_label.text
        outState?.putCharSequence(getString(R.string.main_save_key_height_text), heightText)

        val units = switchedUnitsForLbIn
        outState?.putBoolean(getString(R.string.main_save_key_units), units)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        //restoring all values
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(getString(R.string.main_restore_log_tag), getString(R.string.main_restore_log_message))

        val bmi = savedInstanceState?.getCharSequence(getString(R.string.main_save_key_bmi))
        main_text_result_bmi.text = bmi

        val bmiColor = savedInstanceState!!.getInt(getString(R.string.main_save_key_bmi_color))
        main_text_result_bmi.setTextColor(bmiColor)

        main_button_bmi_norm.setColorFilter(bmiColor)

        val bmiNorm = savedInstanceState.getCharSequence(getString(R.string.main_save_key_norm))
        main_text_bmi_norm.text = bmiNorm

        val colorNorm = savedInstanceState.getInt(getString(R.string.main_save_key_norm_color))
        main_text_bmi_norm.setTextColor(colorNorm)

        val weightText = savedInstanceState.getCharSequence(getString(R.string.main_save_key_weight_text))
        main_text_weight_label.text = weightText

        val heightText = savedInstanceState.getCharSequence(getString(R.string.main_save_key_height_text))
        main_text_height_label.text = heightText

        switchedUnitsForLbIn = savedInstanceState.getBoolean(getString(R.string.main_save_key_units))
        invalidateOptionsMenu()

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //restoring checked or not option from toolbar
        menu?.findItem(R.id.action_switch_unit)?.isChecked = switchedUnitsForLbIn
        return super.onPrepareOptionsMenu(menu)
    }

    private fun goToBmiNormAction() {
        //sending value of bmi and norm to about norm page
        val intent = Intent(this, BmiNormInfoActivity::class.java)
        val result = getBmiResult()
        if (result != 0.0) {
            intent.putExtra(getString(R.string.main_intent_name_bmi), result)
            startActivity(intent)
        }
    }

    private fun getBmiResult(): Double {
        //returns bmi value in double, checking if it is not empty
        var result = main_text_result_bmi.text
        if (result == getString(R.string.string_empty)) {
            result = getString(R.string.double_zero)
            showInvalidInputToast(getString(R.string.main_toast_text_no_bmi_result))
        }
        return result.toString().toDouble()
    }

    private fun showInvalidInputToast(message: String) {
        //showing toast about invalid input and clearing texts and colours
        clearText()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getweight(): String {
        return main_text_weight_input.text.toString()
    }

    private fun getHeight(): String {
        return main_text_height_input.text.toString()
    }

    private fun countBmi() {
        //counting bmi, in imperial or si units, depending of checked option in toolbar, saving com.example.bmi.getResult in SharedPrefs,
        // first of all checking if all things are valid
        val weight = getweight()
        val height = getHeight()
        val result: Double

        if (isInput(weight, height)) {
            result = if (switchedUnitsForLbIn) {
                BMIForLbIn(weight.toInt(), height.toInt()).countBmi()
            } else {
                BMiForKgCm(weight.toInt(), height.toInt()).countBmi()
            }
            val resultText = String.format("%.${2}f", result)
            main_text_result_bmi.text = resultText
            val norm = showBmiNorm(result)
            SharedPrefs.saveBmi(weight, height, resultText, norm, switchedUnitsForLbIn, this)
        }
    }

    private fun switchToLbIn() {
        clearText()
        main_text_weight_label.text = getString(R.string.main_text_weight_second_unit)
        main_text_height_label.text = getString(R.string.main_text_height_second_unit)
    }

    private fun switchToKgCm() {
        clearText()
        main_text_weight_label.text = getString(R.string.main_text_weight_first_unit)
        main_text_height_label.text = getString(R.string.main_text_height_first_unit)
    }

    private fun clearText() {
        //clearing output and color of bmi norm info button
        main_button_bmi_norm.clearColorFilter()
        main_text_result_bmi.text = getString(R.string.string_empty)
        main_text_bmi_norm.text = getString(R.string.string_empty)
    }

    private fun isInput(weight: String, height: String): Boolean {
        //checking if there is any input, then if input is valid
        return if (height == getString(R.string.string_empty) || weight == getString(R.string.string_empty)) {
            showInvalidInputToast(getString(R.string.main_toast_text_no_weight_height))
            false
        } else {
            if (switchedUnitsForLbIn) {
                isValidLbIn(weight.toInt(), height.toInt())
            } else {
                isValidKgCm(weight.toInt(), height.toInt())
            }
        }
    }

    private fun isValidLbIn(weight: Int, height: Int): Boolean {
        return when {
            BMIForLbIn(weight, height).isInRangeHeight() -> {
                showInvalidInputToast(getString(R.string.main_toast_text_invalid_height_input))
                false
            }
            BMIForLbIn(weight, height).isInRangeWeight() -> {
                showInvalidInputToast(getString(R.string.main_toast_text_invalid_weight_input))
                false
            }
            else -> true
        }
    }

    private fun isValidKgCm(weight: Int, height: Int): Boolean {
        return when {
            BMiForKgCm(weight, height).isInRangeHeight() -> {
                showInvalidInputToast(getString(R.string.main_toast_text_invalid_height_input))
                false
            }
            BMiForKgCm(weight, height).isInRangeWeight() -> {
                showInvalidInputToast(getString(R.string.main_toast_text_invalid_weight_input))
                false
            }
            else -> true
        }
    }

    private fun showBmiNorm(bmi: Double): String {
        //displaying bmi norm, changing color of bmi value, norm and norm info button

        val result = bmiResult(bmi)
        main_button_bmi_norm.setColorFilter(ContextCompat.getColor(this, result.color))
        main_text_bmi_norm.text = result.category.toString()
        main_text_bmi_norm.setTextColor(ContextCompat.getColor(this, result.color))
        main_text_result_bmi.setTextColor(ContextCompat.getColor(this, result.color))
        return result.category.toString()
    }

}
