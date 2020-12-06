package com.example.catalog.details.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.Album
import com.example.catalog.R

class albums_adapter(private val albums: List<Album>) :
    RecyclerView.Adapter<albums_adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val relaseDate: TextView
        val cover: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.album_name)
            relaseDate = view.findViewById(R.id.release_date)
            cover = view.findViewById(R.id.album_cover)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.albums_element_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = albums[position].name
        viewHolder.relaseDate.text = viewHolder.itemView.context.getString(R.string.ReleaseDate, albums[position].releaseDate)
        viewHolder.cover.setImageResource(albums[position].cover_id)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = albums.size

}