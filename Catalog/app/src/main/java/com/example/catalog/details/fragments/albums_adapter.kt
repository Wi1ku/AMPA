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
        val name: TextView = view.findViewById(R.id.album_name)
        val relaseDate: TextView
        val cover: ImageView

        init {
            relaseDate = view.findViewById(R.id.release_date)
            cover = view.findViewById(R.id.album_cover)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.albums_element_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = albums[position].name
        viewHolder.relaseDate.text = viewHolder.itemView.context.getString(R.string.ReleaseDate, albums[position].releaseDate)
        viewHolder.cover.setImageResource(albums[position].cover_id)

    }

    override fun getItemCount() = albums.size

}