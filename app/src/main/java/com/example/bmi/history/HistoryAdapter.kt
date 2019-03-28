package com.example.bmi.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class HistoryAdapter(private val list: List<String>) : RecyclerView.Adapter<HistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val history: String = list[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int = list.size

}