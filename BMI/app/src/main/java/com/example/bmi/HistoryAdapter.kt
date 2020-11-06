package com.example.bmi

import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val Dataset: List<BmiRecord>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(TextView: View) : RecyclerView.ViewHolder(TextView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val textView = itemView.findViewById<TextView>(R.id.record)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_record, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = Dataset[position].toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = Dataset.size
}