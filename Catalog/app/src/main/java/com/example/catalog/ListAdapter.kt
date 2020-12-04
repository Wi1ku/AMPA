package com.example.catalog

import android.content.Intent
import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView


class ListAdapter(private val model: AppViewModel) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View, val model: AppViewModel) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val name: TextView
        val category: TextView
        val image: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.Name)
            category = view.findViewById(R.id.Category)
            image = view.findViewById(R.id.imageView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val intent = Intent(v.context, DetailsActivity::class.java).apply {
                putExtra("id", model.bandList.value?.get(layoutPosition)?.id)
            }
            v.context.startActivity(intent)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.record_layout, viewGroup, false)


        return ViewHolder(view, model)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val band = model.bandList.value?.get(position)
        if (band != null){
            viewHolder.name.text = viewHolder.itemView.context.getString(R.string.ListElementName, band.name)
            viewHolder.category.text = viewHolder.itemView.context.getString(R.string.ListElementCategory, band.category)
            viewHolder.image.setImageResource(band.image_id)
        }

    }




    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = model.bandList.value!!.count()

}