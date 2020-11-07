package com.example.bmi

import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val Dataset: List<BmiRecord>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(TextView: View) : RecyclerView.ViewHolder(TextView) {
        val date = itemView.findViewById<TextView>(R.id.date)
        val record = itemView.findViewById<TextView>(R.id.record)
        val measures = itemView.findViewById<TextView>(R.id.measures)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_record, parent, false)

        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = Dataset[position].getTime()
        holder.record.text = Dataset[position].getResult()
        holder.measures.text = Dataset[position].getMeasures()
    }

    override fun getItemCount() = Dataset.size
}