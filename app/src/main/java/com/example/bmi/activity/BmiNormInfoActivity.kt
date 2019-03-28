package com.example.bmi.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.bmi.R
import com.example.bmi.logic.BMIResult.Companion.bmiResult
import kotlinx.android.synthetic.main.activity_bmi_norm_info.*

class BmiNormInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_norm_info)
        setSupportActionBar(norm_info_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bmi = intent.getDoubleExtra(MainActivity.MAIN_INTENT_NAME_BMI, 0.0)
        displayInfo (bmi)
    }

    //display and make useful back arrow
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayInfo(bmi: Double) {
        //displays information about norm, com.example.bmi.getResult of bmi with name of norm in specified color

        val result = bmiResult(bmi)
        norm_info_text_bmi_value.text = bmi.toString()
        norm_info_text_about_norm.text = getString(result.details)
        norm_info_text_norm.text = result.category.toString()
        norm_info_text_norm.setTextColor(ContextCompat.getColor(this, result.color))
        norm_info_text_bmi_value.setTextColor(ContextCompat.getColor(this, result.color))
    }


}
