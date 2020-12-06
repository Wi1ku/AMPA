package com.example.catalog

import android.content.Intent
import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView


class ListAdapter(private val list: MutableList<ListElement>, val onFavouriteClick: (ListElement, Boolean) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(view: View, l: List<ListElement>) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val element = view.findViewById<ConstraintLayout>(R.id.record)
        val name: TextView = view.findViewById(R.id.Name)
        val category: TextView = view.findViewById(R.id.Category)
        val image: ImageView = view.findViewById(R.id.imageView)
        val favourte_btn: ImageButton = view.findViewById(R.id.favorite_button)
        val list = l

        init {
            // Define click listener for the ViewHolder's View.
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View) {
            val intent = Intent(v.context, DetailsActivity::class.java).apply {
                //TODO: remove hardcoded
                putExtra("id", list[layoutPosition].id)
            }
            v.context.startActivity(intent)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.record_layout, viewGroup, false)


        return ViewHolder(view, list)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val band = list[position]
        viewHolder.name.text = viewHolder.itemView.context.getString(R.string.ListElementName, band.name)
        viewHolder.category.text = viewHolder.itemView.context.getString(R.string.ListElementCategory, band.category)
        viewHolder.image.setImageResource(band.image_id)
        if(band.category == "Rock"){
            viewHolder.element.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.slate_gray))
        }
        if(band.category == "Jazz"){
            viewHolder.element.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.powder_blue))
        }
        if(band.category == "Pop"){
            viewHolder.element.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.medium_slate_blue))
        }
        if(band.isFavourite){
            viewHolder.favourte_btn.setImageResource(android.R.drawable.btn_star_big_on)
        }
        else{
            viewHolder.favourte_btn.setImageResource(android.R.drawable.btn_star_big_off)
        }
        
        viewHolder.favourte_btn.setOnClickListener { if(band.isFavourite){
            viewHolder.favourte_btn.setImageResource(android.R.drawable.btn_star_big_off)
        }
        else{
            viewHolder.favourte_btn.setImageResource(android.R.drawable.btn_star_big_on)
        }
            onFavouriteClick(band, band.isFavourite)}

    }




    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.count()

}