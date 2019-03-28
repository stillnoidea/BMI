package com.example.bmi.history

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.bmi.R
import com.example.bmi.logic.BMIResult.Companion.bmiResult

class HistoryHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.history_item, parent, false)) {

    // 5 values that are saved in SharedPrefs
    private var date: TextView? = null
    private var weight: TextView? = null
    private var bmi: TextView? = null
    private var height: TextView? = null
    private var norm: TextView? = null


    init {
        date = itemView.findViewById(R.id.history_text_date_value)
        weight = itemView.findViewById(R.id.history_text_weight_value)
        height = itemView.findViewById(R.id.history_text_height_value)
        bmi = itemView.findViewById(R.id.history_text_bmi_value)
        norm = itemView.findViewById(R.id.history_text_bmi_norm_value)
    }

    fun bind(text: String) {
        //getting specific values from one line of String from SharedPrefs

        val strings = text.split(",")
        weight!!.text = strings[1]
        height!!.text = strings[2]
        bmi!!.text = strings[3]
        date!!.text = strings[5]
        norm!!.text = strings[4]

        val bmi = strings[3].toDouble()
        val result = bmiResult(bmi)
        this.bmi?.setTextColor(ContextCompat.getColor(itemView.context, result.color))
        norm?.setTextColor(ContextCompat.getColor(itemView.context, result.color))
    }

}