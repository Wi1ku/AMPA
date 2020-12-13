package com.example.bmi

import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.Room.BmiRecord

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
        holder.date.text = Fomatter.getTime(Dataset[position].time)
        holder.record.text = Fomatter.getResult(Dataset[position].bmi)
        holder.measures.text = Fomatter.getMeasures(Dataset[position])
    }

    override fun getItemCount() = Dataset.size
}